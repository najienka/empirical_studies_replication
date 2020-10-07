#!/usr/bin/Rscript



path = "/home/user/Documents/studies/study_semantic_structural_relationship/" #directory with txt files
out.file<- ""
file.names <- dir(path, pattern ="result.csv") # file extension


for (i in 1:length(file.names)) 
	{ #begining of for loop

	
    
  tmp.file <- read.table(file.names[i], header=TRUE,sep=",")  #open file - Not sure if you have headers or not
	
	x = cor.test(tmp.file$references, tmp.file$discosemantic, method = "spearman")
   # compute correlation

	attributes(x)
	
#	pvalue=x["p.value"]
	#corr=x["estimate"]


	
	outname<-substr(file.names[i], 1, nchar(file.names[i])-10)
	
	#append the results to the same file so append = true
	#no column IDs or column names from R so both are set to false
write.table(paste(outname,x["estimate"],x["p.value"],sep=","),file=paste("corrdisco.txt"), sep=",", append=TRUE , row.names=FALSE, col.names=FALSE)

 #   write.table(tmp.rules,file=paste(i, "conf",".txt"), sep=",",row.names=FALSE) #write result out to output file
#write(paste0(i, "," tmp.mean), out.file, append=TRUE)


}

