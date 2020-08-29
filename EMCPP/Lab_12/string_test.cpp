#include "../../../../Lab_02/String.h"
#include "gtest/gtest.h"

TEST(StringTest, CorrectlyConstructsFromStdString) {
    std::string test = "test";
    String a{test};

    for (int i = 0; i < test.length(); ++i) {
        EXPECT_EQ(a[i], test[i]);
    }
}

TEST(StringTest, CorrectlyConstructsFromString) {
    std::string test = "test";
    String a{test};
    String b{a};

    for (int i = 0; i < test.length(); ++i) {
        EXPECT_EQ(b[i], test[i]);
    }
}

TEST(StringTest, CorrectlyConstructsFromAssignedString) {
    std::string test = "test";
    String a{test};
    String b = a;

    for (int i = 0; i < test.length(); ++i) {
        EXPECT_EQ(b[i], test[i]);
    }
}

TEST(StringTest, CorrectlyAssignCharacter) {
    std::string test = "test";
    std::string test_changed = "tset";
    String a{test};

    for (int i = 0; i < test.length(); ++i) {
        ASSERT_EQ(a[i], test[i]);
    }

    a[1] = 's';
    a[2] = 'e';

    for (int i = 0; i < test_changed.length(); ++i) {
        EXPECT_EQ(a[i], test_changed[i]);
    }
}

TEST(StringTest, CorrectlyConcatenateIfFirstEmpty) {
    std::string test = "";
    std::string test2 = "test";
    String a{test};
    String b{test2};

    String result = a + b;

    for (int i = 0; i < test2.length(); ++i) {
        EXPECT_EQ(result[i], test2[i]);
    }
}

TEST(StringTest, CorrectlyConcatenateIfSecondEmpty) {
    std::string test = "test";
    std::string test2 = "";
    String a{test};
    String b{test2};

    String result = a + b;

    for (int i = 0; i < test.length(); ++i) {
        EXPECT_EQ(result[i], test[i]);
    }
}

TEST(StringTest, CorrectlyConcatenate) {
    std::string test = "test";
    std::string test2 = "tset";
    std::string concatenated = test + test2;
    String a{test};
    String b{test2};

    String result = a + b;

    for (int i = 0; i < concatenated.length(); ++i) {
        EXPECT_EQ(result[i], concatenated[i]);
    }
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}