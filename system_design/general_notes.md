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
   - Once we have finalized requirements, it is a good idea to define the API.
   - Define the DB schema and identify whether it is read-heavy, write-heavy, or
     both. Knowing the schema also helps determine the database type that
     will be required, e.g., is relational important, or can we use key-value
     store or something.
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
- Consistent hanshing
- Data partitioning
- Queues
- Database technologies (Why does RDBMS not scale? I thought hadoop/spark was
  relational? It scales)
- Load balancing.
- key-value store
- sharding based on key
- key server
- dealing with imbalanced reads from shards (e.g., very popular user on social
  network)
- Learn conceptually how MapReduce works
- ACID transactions in SQL is good for distributed computing for some reason?

## Considerations

- Scalability
- Availability
- Concurrency
- Consistency
- Latency
- Redundancy
- Read heavy versus write heavy

### Scalability

Performance typically declines as scale is added. Not simply algorithmic
performace (even optimized algorithms are not usually capable of constant time
computations), but also network latency, if that is a bottleneck, can become an
issue. This can be dealt with by placing the storage in regional data centers
or using cloud storage/CDNs that do this for you.

#### Efficiency

Efficiency of a scalable system is going to depend on latency (response time)
and throughput (bandwidth). These contribute to the following unit costs:

- Number of messages globally sent by the nodes of the system
- Size of messages

It is these costs which limit the complexity of operations supported by a
distributed system.

### Availability

This is measured in "nines," i.e., 99.9% available is "one nine." This is not
the same thing as reliability, although reliability implies availability.
Availability considerations are usually made on the assumption of unreliable
components. So to ensure availability of a service (from the UX perspective,
this is all that matters), we use redundant components on the assumption that
they will inevitably fail.

### Load Balancing

These are a simple concept. It's just middleware that routes API calls to
redundant servers. It can be located between any two layers, where the target
is distributed. There are multiple algorithms for laod balancing, but this is a
point for in depth discussion only if necessary. The simplest is hashing the
session ID mod N.

Since load balancers are a single point of failure (just like anything), it's
worth discussing how redundant load balancers work. Just use a backup that
monitors the health of the active load balancer, if the one can handle the
traffic.

Load balancers also have to monitor the health of their targets. This could be
done by keeping a cache of "healthy" servers, which is updated periodically by
sending health check RPCs to the services at each endpoint.

### Caching

A cache is in-memory storage of frequently accessed data. This can be a layer
of flash storage between a database and application layer, or flash storage on
the application server itself. An intereesting idea I saw was that one can
cache based on some sort of 80/20 rule, e.g., for a social network, you can
expect that 20% of users will get 80% of the requests. So you can set cache
equal to 20% of the storage volume.

At a high level, there are three schemes for caching data:

1. Write-through cache. Data is written to both cache and storage
   simultaneously.
2. Write around storage. Data is written to storage directly, and the cache is
   filled at read-time.
3. Write back cache. Data is both read and written to the cache layer, which is
   responsible for updating the storage.

While the concept is simple, it introduces a few complicating factors:

#### Cache Invalidation

This is the process for keeping the cached data up to date with the permanent
storage. Write through and write back cache both solve the invalidation problem,
but involve heavy traffic through flash storage, which could degrade over time.
Write around cache can get out of sync, but involves the fewest writes to the
cache.

#### Cache eviction

This is the process for removing data from the cache, either because it is no
longer valid, or because the cache is filling up and needs to have old (less
frequently used) data removed in favor of new data. There are lots of different
eviction policies. Probably off the top of my head I will only be able to
recall first in first out (i.e., a queue), and least recently/frequently used.

#### Content Delivery Networks

This may deserve its own section. These are basically large distributed caches
(usually through a cloud service) that offer low latency due to client
proximity (think Netflix videos). The model is also different, since I believe
they permit reads from the client directly. They are typically used for
delivering large static files to the client, and cache invalidation is a huge
problem here.

### Partitioning

#### Horizontal (row-based) partitioning

Different rows go in different locations based on columnar range. The
partitioning schema may be based on one or more columns, e.g., IDs or ZIP codes.
This can lead to unbalanced partitions if the scheme is not carefully chosen.

#### Vertical (column-based) partitioning and normalization

Store different tables in different servers. This scales less well than
horizontal.

#### Directory based partitioning

Directory server works as a lookup for location based on key. Scales well.
This is how Hadoop works, I am pretty sure.

#### Complications

Partitioning complicates relational integrity. This problem is partially solved
by MapReduce based services like Apache Spark, however it is still a problem. A
common solution here is to denormalize the table (i.e., include more columns at
varying levels in the same table) so that queries over distributed tables
involve less joining between partitions.

See also: Referential integrity, rebalancing.

### CAP Theorem

This is a "theorem" that states that Consistency, availability, and partition
tolerance are a Venn diagram such that $C\cap A\cap P = \emptyset$. The
intersection of C and A is SQL databases, C and P is various NoSQL solutions
like BigTable or MongoDB. The intersection of A and P is labeled with Cassandra
and CouchDB (which are also classified as NoSQL, but I don't know how they
differ from the other intersection.)

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
