[
 Changes since the presentation at 04.APR:
 - 04.APR: corrected the swapped arguments in the case of "i" operations: instruction was updated.
 - All operations (except P ALL) should be done in time proportional to a size of single block 
  -> All operations (except P ALL) should be done in time O(BLOCK_SIZE) (bounded by the size of a block).
 - It means that if the block is of size BS. Then you can do up to a * BS unit-time operations, where BS is some >fixed< constant (depending on your implementation). 
  -> It means that if the block is of size BS. Then you can do up to a * BS unit-time operations, where a is some >fixed< constant (depending on your implementation).

Feel free to ask for clarifications, all questions should be submitted to T. Pikies: tytpikie@student.pg.edu.pl
]

Currently available computers have RAM memories that can be of different speeds.
For example, often a CPU cache memory is present, see https://en.wikipedia.org/wiki/CPU_cache. 
The cache memory is quite often organized into bigger blocks, and whole block is read from or written to main memory at once.
Hence, it might be reasonable to write a list which takes this into account. 

The aim of the project is to implement a list of your choice. Either:
 a) a simple double linked list -- exactly as you have learned during the lectures;
 b) or a block-based double linked list -- a bit richer data structure. 
    This type of list is designed under a stipulation that the elements on the list are small, and the memory is organized into a bigger blocks.
    Here, you have to operate using blocks of memory of a given size, for example 64B, and pack the elements in blocks.
    Observe, you can pack the elements in a block in any reasonable way, for example:
    - The first x bytes can be an address of the previous block.
    - The next x bytes can be an address of the next block.
    - Next there can be a byte signifying how many elements there are in the block. 
    - Then the elements can be present.
    You can propose your own organization but keep in mind that your program has to operate on blocks and use them well.
    To give an example, storing always one element in a block will be not graded positively.  

The data stored on the list are positive integer numbers (8B) -- unsigned long long int type suffices to store them. 
A list can have up to 10 iterators; an iterator point to a particular numbers on the list.
There are two "fixed" iterators BEG and END, pointing to the first and last element, respectively. 
The block of memory can be of minimum size 64B, but can be also larger like 128B up to 1024B.
Your implementations should be able to operate efficiently on blocks of arbitrary size -- do not fix the size of a block to 1024B. 

Interface:
The operations to implement are:
   I x             -- The parameters x is the block size.
                      Use it to initialize the block-based list.
                      In the case of the simple list you can ignore it, or use however you like.
   i x y           -- Initialize the x-th iterator to the position y.
                      The value of y can be either [BEG]inning, or [END], or a number.
                      If it is a number i', then the iterator should be a copy of the i'-th iterator.
                      An iterator can be initialized many times and there should be no memory leaks.
                      The iterators (except BEG and END) are labeled from 0 to 9.
   + x             -- Move the x-th iterator forward.
                      If moving forward is impossible, then the iterator should point to the last element.
   - x             -- Move the x-th iterator backward.
                      If moving backward is impossible, then the iterator should point to the first element.
   .A x y          -- Add the number y before the position x.
                      As previously, x can be either [BEG]inning, [END], or a number of iterator.
                      In the case of an empty list, add the number to the list and initialize BEG and END appropriately.
   A. x y          -- Add the number y after the position x.
                      As previously, x can be either [BEG]inning, [END], or a number of iterator.
                      In the case of an empty list, add the number to the list and initialize BEG and END appropriately.
   R x             -- Remove an element given by the position x.
                      Similarl, x can either [BEG]inning or [END] of list, or a number of iterator.
                      If an element is removed, then all iterators pointing at it should start pointing to the next element.
                      If this is not possible (the element at the end was removed) they should point to the new last element.
                      If this is also not possible (the last element was removed) the pointer should become uninitialized.
   P p             -- Print the number on the position given by p.
                      The parameter p can be of two types.
                      It can be [ALL] which means to print from head to tail.
                      Or it may be a number of iterator -- in the last case print the value pointed.

Concerning the cache-friendly list: keep in mind how A and R operate.
The operation A should be able, if necessary, to construct a new block. 
However, it will be not very good if it will be possible to construct many almost empty blocks -- split the block in half.

Concerning the operation R, observe that removal of an element can make the corresponding block almost empty.
For example occupancy of the block will be less than 1/3 maximum possible.
In such a case, if there is a neighbouring block with number of jobs more than this minimum, then move a job from that block.
If this is not possible, merge two neighbouring blocks; provided that there are at least 2 blocks, of course.  

Finally, all the operations (except P ALL) should work in time O(BLOCK_SIZE).
You shouldn't traverse whole list to add or remove a single element, the time can be proportional to the size of a block, at worst. 


Grading: 
 - To obtain up to 100% implement the block-based list.
   If you would like to implement something easier, implement a simple list for up to 80%.
   In both cases, the parts: adding, removing, and iterators are ~ equally important with respect to grading.
 - In the case of block-based list it is absolutely crucial that the program operates on blocks of memeory of fixed size and use them well.
   Moreover, it is crucial that if the elements are neighbours on the list (from logical viewpoint), then they will be either in the same memory block or in consecutive blocks.  
   Also, it will be not perfect if metadata about block (for example the addresses of neighbouring blocks, or number of elements) will be stored in seperate memory.
 - The program should scan all the input, process commands, and terminate.
   If the program does not end after the last command is handled (check if stdin/cin is closed), then a penalty will be given.
 - A hefty penalty will be given for an exceptional inefficiency, like traversing the whole list to add a single element.
 - All operations (except P ALL) should be done in time O(BLOCK_SIZE) (bounded by the size of a block).
 - The program should deallocate all the memory before ending.
 - Do not use string class. 

Tips:
 - As usual, check if everything was uploaded to STOS on time.
 - The advanced version is not very hard. However, it might take slightly more time to write than the simple one. So, start early.
   Maybe, it would be also resonable to implement the simple version, which is trully easy, to have a contingency plan. 
   You can upload both version to STOS: tpikies_project_CacheList_simplified is for simplified version, and tpikies_project_CacheList_normal for normal (block-based) version. 
 - Perhaps it is reasonable to start with the least set of functions that show that the program works.
   For example, first implement the addition of elements, then removal, then start tinkering with iterators.
 - Write many small checks: if the indices are right, if the blocks are not full when adding an element, if the next block exists when it should, etc.
   Also, perhaps, write a procedure checking in general that the list is consistent.
 - In the case of block-based list it is very reasonable (as usual) to shift the most logic into small functions guaranteeing some property.
   For example, into a simple function shifting an element between blocks guaranteeing that after the shift all the blocks and iterators are correct.
 - To find that the input has ended use the fact that commands have known number of arguments and use code like:
   char command [100];
   while(true)
   {
    scanf("%s", command);
    if(feof(stdin)!=0)
    {
     break;
    }
    // Parsing Commands 
   }
   Simply, the while loop ends when the input ends.
 - You can use content of string.h -- do not bother with writing text->number coversion by hand.
   In particular, strcmp/sscanf can be used to determine if the given text contains number, or a fixed text, etc.

Tests:
1,2: simple addition
3,4: simple removal
5,6: addition and removal combined
7,...,11: basics of iterators
12: big test for addition
13: big test for removal
14: big test for 2 iterators
15: big test for 10 iterators


Detailed clarifications:

1. Spliting blocks:
To illustrate the splitting of the block for A operations, assume that you have a block B and you would like to add 6 -- between 5 and 7.
In this case you may split block B into B' and B'' and add the element after splitting. 

         B               
   +-----------+   
   |prev block |
   |next block |
   |     6     |
   +-----------+
   |     1     |
   |     2     |
   |     3     |
   |     4     |
   |     5     |
   |     7     |
   +-----------+

Which is changed into:

         B'                B''
   +-----------+     +-----------+
   |prev block |     |B'         |
   |B''        |     |next block |
   |     3     |     |     4     |
   +-----------+     +-----------+
   |     1     |     |     4     |
   |     2     |     |     5     |
   |     3     |     |     6     |
   |           |     |     7     |
   |           |     |           |
   |           |     |           |
   +-----------+     +-----------+

2. Shifting elements for the R operations:
To illustrate the situation assume that you have the following situation.
         B                 B'                B''
   +-----------+     +-----------+     +-----------+
   |prev block |     |B          |     |B'         |
   |B''        |     |B''        |     |next block |
   |     3     |     |     2     |     |     2     |
   +-----------+     +-----------+     +-----------+
   |     1     |     |     4     |     |     6     |
   |     2     |     |     5     |     |     7     |
   |     3     |     |           |     |           |
   |           |     |           |     |           |
   |           |     |           |     |           |
   |           |     |           |     |           |
   +-----------+     +-----------+     +-----------+

You would like to remove 4. In this case the occupancy of the block will be low. 
Hence you can shift 3 from B to B' and then remove 4 -- the occupancy will be good.

         B                 B'                B''
   +-----------+     +-----------+     +-----------+
   |prev block |     |B          |     |B'         |
   |B''        |     |B''        |     |next block |
   |     2     |     |     2     |     |     2     |
   +-----------+     +-----------+     +-----------+
   |     1     |     |     3     |     |     6     |
   |     2     |     |     5     |     |     7     |
   |           |     |           |     |           |
   |           |     |           |     |           |
   |           |     |           |     |           |
   |           |     |           |     |           |
   +-----------+     +-----------+     +-----------+

3. Merging blocks:
To illustrate, assume that you would like to remove 3 from the block presented above. You cannot shift any element. 
But you can merge B' and B'' and then remove 3 -- ocupancy still will be good.

         B                B'+B''      
   +-----------+     +-----------+
   |prev block |     |B          |
   |B'+B''     |     |next block |
   |     2     |     |     3     |
   +-----------+     +-----------+
   |     1     |     |     5     |
   |     2     |     |     6     |
   |           |     |     7     |
   |           |     |           |
   |           |     |           |
   |           |     |           |
   +-----------+     +-----------+

Observe that the time required to perform all the operations is ~ max number of elements in 3 blocks.



Sample Data:

Sample Input 1:
I 64
.A BEG 10
P ALL
.A BEG 9
P ALL
.A BEG 8
P ALL
.A BEG 7
P ALL
.A BEG 6
P ALL
R BEG
P ALL
R BEG 
P ALL
R BEG
P ALL
R BEG
P ALL
.A BEG 5
P ALL
.A BEG 4
P ALL
.A BEG 3
P ALL
.A BEG 2
P ALL
.A BEG 1
P ALL

Corresponding output:
10 
9 10 
8 9 10 
7 8 9 10 
6 7 8 9 10 
7 8 9 10 
8 9 10 
9 10 
10 
5 10 
4 5 10 
3 4 5 10 
2 3 4 5 10 
1 2 3 4 5 10 

Sample Input 2:
I 64
A. BEG 1
A. END 2
A. END 3
A. END 4
A. END 6
P ALL
i 0 BEG
+ 0
+ 0
R 0 
P ALL
A. 0 5
P ALL
.A 0 3
P ALL

Corresponding output:
1 2 3 4 6 
1 2 4 6 
1 2 4 5 6 
1 2 3 4 5 6 



Questions and Answers:

Q&A:
Q1. What is a block? A memory array a structure?
A1. It is a memory array - a simple continues block of memory. Structurize this block suitably.

Q2. What means time proportional to the block size?
A2. It means that if the block is of size BS. Then you can do up to a * BS unit-time operations, where a is some >fixed< constant (depending on your implementation).
    For example, in the case of merging the block -- observe that there were some shifts, additions, perhapes deallocations.
    Observe, that in any resonable implementation the number of operations at all can be bounded by 10 (or 3 or 7 or 20) times BS.
    The precise constant is not that important.
    It is important that you are not doing, for example, BS^2 operations, or n (where n is the number of the elements in the whole list) operations.
    The last case would be a true disaster. 
