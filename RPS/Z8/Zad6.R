# Title     : TODO
# Objective : TODO
# Created by: seqre
# Created on: 02/05/2020

# Zad 6
dat <- c(43, 37, 50, 51, 58, 105, 52, 45, 45, 10)

# a)
avg <- mean(dat)
median <- median(dat)
quantiles <- quantile(dat)
std <- sd(dat)
cat("Average: ", avg, "\n")
cat("Median: ", median, "\n")
cat("Quantiles: ", quantiles, "\n")
cat("Standard deviation: ", std, "\n")

# b)
iqr <- IQR(dat)
isTooBig <- function(x) {
  ifelse(x < median - iqr | median + iqr < x, TRUE, FALSE)
}
bigValues <- Filter(isTooBig, dat)
cat("Outlier values: ", bigValues, "\n")

# c)
cleanDat <- setdiff(dat, bigValues)
cat("Cleaned data: ", cleanDat, "\n")
avgClean <- mean(cleanDat)
medianClean <- median(cleanDat)
quantilesClean <- quantile(cleanDat)
stdClean <- sd(cleanDat)
cat("Average after cleaning data: ", avgClean, "\n")
cat("Median after cleaning data: ", medianClean, "\n")
cat("Quantiles after cleaning data: ", quantilesClean, "\n")
cat("Standard deviation after cleaning data: ", stdClean, "\n")

# d)
# Wartości odstające mają duży wpływ na statystyki opisowe.
# Odchylenie standardowe znacznie się zmniejszyło, bo zmniejszył się rozrzut wartości.
# Średnia także się zmieniła.
# Mediana zmieniła się nieznacznie.
# W kwantylach zmieniły się tylko wartości krańcowe, środkowe są prawie take same.