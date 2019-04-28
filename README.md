# Exercise

Given a list of calls with the following format:

    time_of_start;time_of_finish;call_from;call_to

And the following rules:

 - The first 5 minutes of each call are billed at 5 cents per minute
 - The remainder of the call is billed at 2 cents per minute
 - The caller with the highest total call duration of the day will not be charged (i.e., the caller that has the highest total call duration among all of its calls)

Calculate the total cost for these calls.

Here's a sample file:

    09:11:30;09:15:22;+351914374373;+351215355312
    15:20:04;15:23:49;+351217538222;+351214434422
    16:43:02;16:50:20;+351217235554;+351329932233
    17:44:04;17:49:30;+351914374373;+351963433432

## Interface

The interface we're expecting is:

    $ your_script input_file


As for output, we're expecting that your code print the total in decimal format, with no currency symbols; e.g.:

    15.05

# Execute exercise

    ./gradlew build

    ./calculate-cost.sh input_file

