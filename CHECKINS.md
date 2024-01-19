**Group 16 Project 1 Check-in 1**\
Members: Jeffrey Chow, Dave Borrel, Taylor Foster, William Gumboc, Sophia Kim

_What is the high-level purpose of your DSL? What kind of users is it aimed at? What will it enable users to do?_

The high-level purpose of our DSL is to create a language that makes composing music easier for users. Our targeted users include composers, musicians, and music enthusiasts. With this DSL, users will be able to compose music and generate sheet music in the end. When it comes to music, there is a lot of duplication of rhythm, repetition in music patterns, and structured compositions of notes. As such, this DSL will allow users to compose more efficiently.

_What are the 2-3 rich features of your DSL? A rich feature should be more complex than a choice in a set (e.g., the ability to choose between colours for a title is not a “rich” feature). What customisation will each feature enable? Which features can be combined to interact in useful or creative ways?_

**Feature 1: Tuples**\
Users can use tuples as a feature where they can define each note’s pitch, length, and modifier. For example:

note1 = (C, eighth, sharp);\
// note1 is an eighth note set to C#

note1[0] -= 1\
// note1[0] modifies the note assigned to note1 (first in the tuple) and sets the note down by 1, which would become D#. Values can be incremented
and decremented from 0.5 (i.e. from C# to C is note1[0] -= 0.5)

**Feature 2: Loops**\
Users can use loops as a feature that groups different notes together and loop through them x times for repetition. For example:

// groups note1, note2, note3, note4, note5\
group_fun = (note1, note2, note3, note4, note5)

// loops through group_fun 3 times\
loop group_fun times 3;

**Feature 3: Functions**\
Users can use different functions that can manipulate the notes, such as creating chords, ties, and controlling volume. For example:

// note1, note2, and note3 will play at the same time\
chord1 = chord(note1, note2, note3)

// note4, note5, note6 will be connected with a tie\
tie1 = tie(note4, note5, note6)

// sets bars 1 to 4 a volume of f, meaning “forte”\
// other volume options would be (quietest -> loudest):\
// ppp (triple piano), pp (pianissimo), p (piano), mp (mezzo piano), mf (mezzo forte), f (forte), ff (fortissimo), fff (triple forte)\
make volume of bar 1 to 4 to be f;


**Feature 4: Conditionals**\
Users can write conditions for each note or group of notes. This allows them to customize notes if it passes a condition that the user sets. For example:

// this code will reduce each notes’ volume by 1\
//     if the number of bars that group_fun encompasses is greater than 4\
if (group_fun.bar greater than 4) {\
for each note in group_fun {\
note.volume -= 1\
}\
}

// and then repeats the group of notes in group_fun 9 times\
loop group_fun times 9;

**Feature 5: Mutability**\
Users can mutate notes through each parameter. This allows for manipulating sets or subsets of notes after its’ been declared.

// this code will add notes in group_fun 3 times, and each time, the notes will decrement by 1 pitch
for i in loop 3:\
add(group_fun)\
group_fun = group_fun.all(-1)


_Example snippets of your DSL that illustrate at least each rich feature, and any interesting interaction between those._

note1 = (B, quarter, none);\
note2 = (C, quarter, none);\
note3 = (d, quarter, none);

groupA = tie(note1, note2, note3)

loop n in groupA each (0, 2) note -1 2\
Example syntax: loop [variable] in [set of tuples] [each/all] [optional index(es) if 'each'] [note/beat] [pitch change] [volume change]


_Feedback from TA discussion._\
Our TA provided positive feedback to this idea and gave us her approval. Some feedback we received included having the ability to manipulate subsets of notes, which we’ve taken into account by indexing into the sets.

