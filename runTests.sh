rm -r Front/transactions
mkdir Front/transactions
cd Front

for i in ../Input_Files/*
do
	echo "running $i"
	java Mohawk currentDVDFile.txt < $i
done

cd ..
rm -r merged
mkdir merged

for f in Front/transactions/transaction*.txt
do
	#echo "processing $f file"
	cat $f >> merged/newTransaction.txt
done

echo "00                           000   000.00" >> merged/newTransaction.txt

cd Back

java Backend ../merged/newTransaction.txt masterDVDFile.txt masterDVDFile.txt ../Front/currentDVDFile.txt

#echo "difference transaction output"
#n=1
#for j in ./transactions/*
#do
#	diff $j ./test_files/Expected_Transaction_Files/$n-transaction.txt > ./test_files/Transaction_Diff/$n-result.txt
#	let n=$n+1
#done

#echo "difference terminal output"

#q=1
#for l in ./test_files/Expected_Output_Files/*
#do
#	diff ./test_files/Actual_Output/$q-output.txt $l > ./test_files/Test_Results/$l-result.txt
#	let q=$q+1
#done

