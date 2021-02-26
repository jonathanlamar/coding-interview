# System Design Interview

As part of my Splunk interview (March 2021), I will have to do a system design
interview. These are much higher level, but not any less technical. They
involve diagramming out a system quickly and discussing reliability,
availability, scalability, etc.

These problems are intentionally vague and open ended, and are meant to test
your _design_ skills. However, just as with coding interviews, there are some
frequently re-used concepts.

## Playbook

Here is a playbook for the system design interview.

1. Understand the problem and establish scope.
   - What features are required?
   - What are the scale requirements?
   - Solidify requirements and make reasonable assumptions about traffic/volume.
     This allows you to do back of the envelope calculations to establish
     limits.
2. Propose a high level design and get buy-in.
   - Try to collaborate with the interviewer. This is not only good because it
     helps her evaluate you as a coworker, but you can also get early feedback
     to avoid getting painted in a corner.
   - Do back of the envelope calculations
3. Design deep dive.
   - Once the goals and scope are agreed upon and a rough blueprint is sketched
     out, you now prioritize components to focus in on and address
     volume/scalability concerns **without getting bogged down in details**
   - Select the most important features to discuss.

## Terms

- Caching
- Database technologies (Why does RDBMS not scale? I thought hadoop/spark was
  relational? It scales)
- Load balancing.
- key-value store
- sharding based on key
- key server
- dealing with imbalanced reads from shards (e.g., very popular user on social
  network)
- Learn conceptually how MapReduce works

## Considerations

- Scalability
- Concurrency
- Consistency
- Availability
- Latency
- Redundancy
- Read heavy versus write heavy

## Thought provoking examples

- One database for writes that mirrors to redundant copies for reads. If the
  driver DB goes down, promote one of the workers to take its place.
- Load balancing plays a similar role to caching in that it mitigates excessive
  requests to an API by providing redundant endpoints. Both are buffers between
  layers. Caching is a buffer between server and database, while load balancer
  is a buffer between the client and server.
- Denormalizing a database speeds up reads
- Using CDNs for static content - reminds me of the code deployment system
  example from the Clement video.
