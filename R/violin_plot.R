#install.packages("ggplot2")
library("ggplot2")

x<-read.csv("/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/projects_summary.csv",header=T,sep=",",dec=".")

title <- names(x)
title
xR <- nrow(x)
xR

gdp.plot <- ggplot(data = x, border = 1, 
                   mapping = aes(y = classes, x = ID)) +
  geom_violin() +
  ylab("") +
  xlab("") +
  #scale_fill_grey() + 
  theme_bw() + #white background and gray grid lines
  #add panel.border = element_blank() to remove border/frame
  theme(panel.grid.major = element_blank(),
                            panel.grid.minor = element_blank(), axis.line = element_line(colour = "black")) #removing grid lines from background
#https://felixfan.github.io/ggplot2-remove-grid-background-margin/

# Print plot
print(gdp.plot)