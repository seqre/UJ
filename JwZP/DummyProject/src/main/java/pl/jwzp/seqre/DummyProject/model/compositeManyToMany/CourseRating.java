package pl.jwzp.seqre.DummyProject.model.compositeManyToMany;

import javax.persistence.*;

@Entity
class CourseRating {

    @EmbeddedId
    CourseRatingKey id;

    @ManyToOne
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    Course course;

    int rating;
}
