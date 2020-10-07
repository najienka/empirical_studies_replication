x<-read.csv("vsmdisco1.csv",header=T,sep=",",dec=".")
zz <- file("vsmdisco1.txt","w")
title <- names(x)
writeLines(paste(title[1],title[2],title[3],title[4],title[5],
    "Chisq P Value",sep=","),con=zz,sep="\n")

xR <- nrow(x)

sam<-array(dim=c(2,2))

for (i in 1:xR)
{
    sam[1,] <- c(x[i,2],x[i,3])
    sam[2,] <- c(x[i,4],x[i,5])

    pv<- chisq.test(sam)$p.value

    writeLines(paste(x[i,1],x[i,2],x[i,3],x[i,4],x[i,5],pv,sep=","),
	   con=zz,sep="\n")
}
close(zz)
