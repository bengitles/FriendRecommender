Names: Ben Gitles and Drew Trager

This part of our project just deals with the MapReduce algorithm to recommend friends. While we truly believe
that the logic of our MapReduce is sound, we have not been able to verify it because our iter function did not
work properly. When we would run the iter command in Terminal, only the mapper function would be called, but
not the reducer function. We were unable to determine why, but we believe that if we overcame this one bug,
then our recommender would work perfectly well.

There is a sample set of mocked-up input data in "inputDir" that can get passed into the init mapper. There is
also a "snapshot", so to speak, of our data from our actual PennBook that can also be put into the init mapper.