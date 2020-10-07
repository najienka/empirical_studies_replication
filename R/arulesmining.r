#!/usr/bin/Rscript



library(arules)


path = "/home/user/Documents/studies/study_semantic_cochange_relationship/semantic_logical/arules_test/" #directory with txt files
out.file<- ""
file.names <- dir(path, pattern ="changed.txt") # file extension


for (i in 1:length(file.names)) 
	{ #begining of for loop

	
    tmp.file <- read.table(file.names[i], header=TRUE,sep=",")  #open file - Not sure if you have headers or not
	#min conf was 0.1
	tmp.rules <- apriori(tmp.file, parameter=list(supp=0.01,conf=0.05,minlen=2))
   # tmp.mean <- mean(tmp.file1$V4) #apply command - e.g. mean



	#here, we do not append the results to the same file so append = false or not set 
write(tmp.rules,file=paste0(substr(file.names[i], 1, nchar(file.names[i])-4), "conf",".txt"), sep=",",row.names=FALSE)

 #   write.table(tmp.rules,file=paste(i, "conf",".txt"), sep=",",row.names=FALSE) #write result out to output file


#write(paste0(i, "," tmp.mean), out.file, append=TRUE)
message (file.names[i])

	}

