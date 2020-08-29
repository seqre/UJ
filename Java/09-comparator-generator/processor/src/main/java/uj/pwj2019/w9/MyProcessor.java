package uj.pwj2019.w9;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes({"uj.pwj2019.w9.MyComparable"})
public class MyProcessor extends AbstractProcessor {
    private final static Comparator<Element> comparator = (o1, o2) -> {
        ComparePriority a1 = o1.getAnnotation(ComparePriority.class);
        ComparePriority a2 = o2.getAnnotation(ComparePriority.class);

        if (a1 != null && a2 != null) return a1.value() - a2.value();
        else if (a1 != null) return -1;
        else if (a2 != null) return 1;
        else return 0;
    };

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Class annotation: " + annotation.getQualifiedName());
            annotatedElements.forEach(this::processElement);
        }
        return true;
    }

    private void processElement(Element e) {
        final TypeElement clazz = (TypeElement) e;
        final String className = clazz.getQualifiedName().toString();

        try {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating: " + className + "Comparator");
            final JavaFileObject file = processingEnv.getFiler().createSourceFile(className + "Comparator");
            final String packageName = packageName(className);

            try (SmartWriter out = new SmartWriter(file.openWriter())) {
                if (packageName != null) {
                    out.write("package " + packageName + ";");
                    out.emptyLine();
                }

                out.startClass(clazz.getSimpleName().toString());
                out.startBlock("public int compare(" + className + " a, " + className + " b) {");
                createReturns(out, clazz);
                out.write("return 0;");
                out.endBlock();
                out.endClass();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String packageName(String className) {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        return packageName;
    }

    private void createReturns(SmartWriter out, TypeElement e) {
        final List<? extends Element> enclosedElements = e.getEnclosedElements();

        out.write("int result;");
        enclosedElements.stream()
                .filter((element) -> element.asType().getKind().isPrimitive() && !element.getModifiers().contains(Modifier.PRIVATE))
                .sorted(comparator)
                .forEach(o -> addReturn(out, o));
    }

    private void addReturn(SmartWriter out, Element e) {
        final VariableElement ve = (VariableElement) e;
        final TypeElement typeElement = processingEnv.getTypeUtils().boxedClass(processingEnv.getTypeUtils().getPrimitiveType(ve.asType().getKind()));
        final String varType = typeElement.getSimpleName().toString();
        final String varName = ve.getSimpleName().toString();
        out.write("result = " + varType + ".compare(a." + varName + ", b." + varName + ");");
        out.write("if (result != 0) return result;");
    }

    private static class SmartWriter extends PrintWriter {
        int tab;

        public SmartWriter(Writer out) {
            super(out);
            this.tab = 0;
        }

        @Override
        public void write(String s) {
            try {
                if (tab < 0) throw new WrongCodeException("Too many block endings");
                super.write("\t".repeat(tab) + s + "\n");
            } catch (WrongCodeException e) {
                e.printStackTrace();
            }
        }

        public void startClass(String className) {
            write("import javax.annotation.processing.Generated;");
            emptyLine();
            write("@Generated(\tvalue=\"uj.pwj2019.w9.MyProcessor\",\n"
                    + "\t".repeat(tab + 3) + "comments=\"Simple Comparator Generator\")");
            write("class " + className + "Comparator {");
            tab++;
        }

        public void startBlock(String code) {
            write(code);
            tab++;
        }

        public void emptyLine() {
            write("");
        }

        public void endBlock() {
            tab--;
            write("}");
        }

        public void endClass() {
            try {
                endBlock();
                if (tab != 0) throw new WrongCodeException("Incorrectly closed parentheses");
            } catch (WrongCodeException e) {
                e.printStackTrace();
            }
        }
    }

    private static class WrongCodeException extends Exception {
        public WrongCodeException(String message) {
            super(message);
        }
    }
}

