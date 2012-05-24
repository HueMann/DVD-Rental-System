runTimes = 2

while [[ $runtimes -le 7 ]] 
do
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
	name = $runtimes - 1
	java Backend ../merged/newTransaction.txt masterDVDFile$name.txt masterDVDFile$runtimes.txt ../Front/currentDVDFile.txt

	cd ..
	
	(( runtimes++ ))
done
