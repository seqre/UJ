# Title     : TODO
# Objective : TODO
# Created by: seqre
# Created on: 02/05/2020

# Zad 5
years <- c(1790, 1800, 1810, 1820, 1830, 1840, 1850, 1860, 1870, 1880, 1890, 1900, 1910, 1920, 1930, 1940, 1950, 1960,
           1970, 1980, 1990, 2000, 2010)
population <- c(3.9, 5.3, 7.2, 9.6, 12.9, 17.1, 23.2, 31.4, 38.6, 50.2, 63.0, 76.2, 92.2, 106.0, 123.2, 132.2, 151.3,
                179.3, 203.3, 226.5, 248.7, 281.4, 308.7)

# a)
plot(years, population)
# Widać trend wzrostowy, populacja rośnie.

# b)
increments <- numeric(length(years) - 1)
for (i in 1:(length(years) - 1)) {
  increments[i] <- population[i + 1] - population[i]
}
avgInc <- mean(increments)
medianInc <- median(increments)
varInc <- var(increments)
cat("Increments average: ", avgInc, "\n")
cat("Increments median: ", medianInc, "\n")
cat("Increments variance: ", varInc, "\n")
# Populacja USA w ciągu dekady rośnie o coraz większą wartość

# c)
plot(years[2:length(years)], increments)
# Przyrost populacji USA ciągle rośnie

# d)
relativeIncrements <- numeric(length(years) - 1)
for (i in 1:(length(years) - 1)) {
  relativeIncrements[i] <- (population[i + 1] - population[i]) / population[i]
}
avgRInc <- mean(relativeIncrements)
medianRInc <- median(relativeIncrements)
varRInc <- var(relativeIncrements)
cat("Relative increments average: ", avgRInc, "\n")
cat("Relative increments median: ", medianRInc, "\n")
cat("Relative increments variance: ", varRInc, "\n")

# e)
plot(years[2:length(years)], relativeIncrements)
# Widać trend spadkowy.

# f)
# Można spodziewać się ujemnej korelacji.

# g)
correlation <- cor(increments, relativeIncrements, method = "pearson")
cat("Correlation: ", correlation, "\n")