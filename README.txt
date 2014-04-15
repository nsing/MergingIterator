DETAILS:

I. Solution has been implemented according to the specs.

II. Extracting MergingIterator.zip will give a folder called MergingIterator. It is a standard maven project.

III. Code was tested with Java 1.6 (jdk1.6.0_35). It is aleady compiled but can be re-build again using 'mvn clean install' in the root folder (MergingIterator).

IV. Solution class is file MergingIterator.java (package com.exercise and location MergingIterator\src\main\java\com\exercise).

V. Class MergingIterator also contains an inner class called IteratorWrapper which is a wrapper containing an Iterator and its current value.

VI. Solution is based on a PriorityQuque which uses a comparator created from input comparator. Logic puts IteratorWrapper objects in the queue, access them one by one and maintains queue to get sorted values. Logic also eliminates duplicate elements if expected. Code has necessary comments to explain the logic.

VII. Junit test code is file MergingIteratorTest.java (package com.exercise and location MergingIterator\src\test\java\com\exercise).
   The class contains following 7 test functions:
   1. uniqueAscendingTest
   2. uniqueAscendingExceptionTest
   3. duplicateAscendingTest
   4. uniqueDescendingTest
   5. duplicateDescendingTest
   6. duplicateAscendingFileTest
   7. uniqueAscendingFileTest
   Comments for above functions are there in the code
   
   Folder MergingIterator\src\test\resources\com\exercise contains following text files which provides data to some test functions:
   1. Input.txt: Contains input for Junit test functions duplicateAscendingFileTest and uniqueAscendingFileTest
   2. Result-Duplicate.txt: Contains expected output for Junit test function duplicateAscendingFileTest
   3. Result-Unique.txt: Contains expected output for Junit test function uniqueAscendingFileTest
   
