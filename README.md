# Concurrent Systems - Implementaion in JAVA-8
---

# `Introduction:`
- Concurrent systems refer to computer systems or software applications that are designed to handle multiple tasks or processes simultaneously.
- Instead of executing tasks sequentially, concurrent systems allow for overlapping or simultaneous execution of tasks, which can lead to improved performance, responsiveness, and resource utilization.
- These systems are especially important in modern computing, where multi-core processors and distributed computing environments are common.
- #### `Key Concepts and Considerations:`
	- `Concurrency vs. Parallelism:` Concurrency and parallelism are related but distinct concepts. Concurrency is about managing multiple tasks that are potentially running in overlapping time periods, while parallelism involves executing tasks simultaneously using multiple processors or cores.
	- `Threads and Processes:` In concurrent systems, tasks are often implemented as threads or processes. Threads are lightweight units of execution within a process, while processes are independent instances of a program. Both threads and processes can run concurrently.
	- `Concurrency Models:` There are various concurrency models and approaches, including multi-threading, multi-processing, and event-driven programming. The choice of model depends on the specific requirements of the application.

---

# `Featured:`
- #### `Wait-Free MRMW from SRSW:`
	- (Multiple Readers, Multiple Writers) from (Single Reader, Single Writer):
	- This concept likely involves transforming a `Single Reader`, `Single Writer` (SRSW) lock or data structure into one that supports `multiple concurrent` readers and `writers` without causing contention or requiring `locks`. `Wait-free` means that every operation finishes in a `finite` number of steps regardless of contention, providing high concurrency and predictable performance.
	- #### `Example:`
		```java
		public class AtomicMRMW<T> implements Register<T>{
			int capacity;
			private StampedValue<AtomicMRSW<T>>[] a_table; // array of atomic MRSW registers
			@SuppressWarnings("unchecked")
			public AtomicMRMW(int capacity, T init) {
				this.capacity = capacity;
				this.a_table = new StampedValue[this.capacity];
				for (int i = 0; i < this.capacity; i++) {
					this.a_table[i] = new StampedValue<>(new AtomicMRSW<>(init, this.capacity));
				}
			}
			public void write(T value) {
				String threadName = Thread.currentThread().getName();
				int me = Integer.parseInt(threadName.substring(threadName.length() - 1));
				StampedValue<AtomicMRSW<T>> maxStampedValue = this.a_table[0];
				for (int i = 0; i < this.capacity; i++) {
					maxStampedValue = StampedValue.max(maxStampedValue, this.a_table[i]);
				}
				this.a_table[me] = new StampedValue<>(maxStampedValue.stamp + 1, new AtomicMRSW<>(value, this.capacity));
			}
			public T read() {
				StampedValue<AtomicMRSW<T>> maxStampedValue = StampedValue.max(this.a_table);
				return maxStampedValue.value.read();
			}
		}
		```
- #### `Filter Lock:`
	- The Filter Lock is a `synchronization` primitive that allows `multiple` threads to coordinate access to a `critical section` of code.
	- It is a `generalization` of the `Bakery Lock` and uses an array of `flags` to indicate a `thread's intent` to enter the critical section. Threads must `wait` until their turn based on their flag's `position` in the `array`.
- #### `Bakery Lock:`
	- The Bakery Lock is a simple `synchronization` algorithm that provides mutual exclusion for `multiple` threads or processes.
	- Each thread is assigned a `unique` "`ticket`" number when it enters the `critical section`, and threads must wait until their ticket number is called before they can proceed. It ensures `fairness` in resource allocation.
	- #### `Example:`
		```java
		import java.util.concurrent.TimeUnit;
		import java.util.concurrent.locks.Condition;
		import java.util.concurrent.locks.Lock;

		public class Bakery implements Lock {
			private volatile boolean[]  flag;
			private volatile int[] label;
			private int numThreads;
			public Bakery (int num) {
				// Code Here
			}
			@Override
			public void lock() {
				// Code Here
			}

			/* SOme Code */

			public int filterThread(String thread) {
				return Integer.parseInt(thread.substring(thread.length() - 1));
			}

			public int max_label(int[] labels_arr) {
				// Code Here
				return maxValue + 1;
			}
		}
		```
- #### `MCS Queue Lock:`
	- (Mellor-Crummey and Scott) Queue Lock
	- The MCS Queue Lock is a `scalable` and `efficient` lock for managing access to `shared resources` in a `multithreaded` system. It's designed for use in systems with many threads. Threads `enqueue` themselves in a `queue` `data structure` and take turns accessing the `critical section` based on the order they joined the queue.
- #### `Timeout Lock:`
	- Timeout Locks are `synchronization` mechanisms that allow threads to `attempt` to `acquire` a lock on a resource with a specified `timeout period`.
	- If the lock cannot be `acquired` within the given `time frame`, the thread can either `give up` or `take alternative` actions. This helps `prevent` threads from waiting indefinitely.

---
---

# `Requirements before running codes:`

- Install an `IDE` that `compiles` and `runs` Java codes. Recommendation `VS Code`
- How to setup `WSL` Ubuntu terminal shell and run it from `Visual Studio Code:`
  visit: https://www.youtube.com/watch?v=fp45HpZuhS8&t=112s
- How to Install `Java JDK 17` on `Windows 11:` https://www.youtube.com/watch?v=ykAhL1IoQUM&t=136s
- #### `Installing Oracle JDK on Windows subsystem for Linux`.

  - Run WSL as Administrator
  - set -ex
  - NB: Update links for other JDK Versions
  - export JDK_URL=http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.tar.gz
  - export UNLIMITED_STRENGTH_URL=http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip
  - wget --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" ${JDK_URL}
  - Extract the archive: tar -xzvf jdk-*.tar.gz
  - Clean up the tar: rm -fr jdk-*.tar.gz
  - Make the jvm dir: sudo mkdir -p /usr/lib/jvm
  - Move the server jre: sudo mv jdk1.8* /usr/lib/jvm/oracle_jdk8
  - Install unlimited strength policy: wget --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" ${UNLIMITED_STRENGTH_URL}
  - unzip jce_policy-8.zip
  - mv UnlimitedJCEPolicyJDK8/local_policy.jar /usr/lib/jvm/oracle_jdk8/jre/lib/security/
  - mv UnlimitedJCEPolicyJDK8/US_export_policy.jar /usr/lib/jvm/oracle_jdk8/jre/lib/security/
  - sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/oracle_jdk8/jre/bin/java 2000
  - sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/oracle_jdk8/bin/javac 2000
  - sudo echo "export J2SDKDIR=/usr/lib/jvm/oracle_jdk8 export J2REDIR=/usr/lib/jvm/oracle_jdk8/jre export PATH=$PATH:/usr/lib/jvm/oracle_jdk8/bin:/usr/lib/jvm/oracle_jdk8/db/bin:/usr/lib/jvm/oracle_jdk8/jre/bin export JAVA_HOME=/usr/lib/jvm/oracle_jdk8 export DERBY_HOME=/usr/lib/jvm/oracle_jdk8/db" | sudo tee -a /etc/profile.d/oraclejdk.sh

---

# `Makefile:`

##### NB: A makefile Is Included to compile and run the codes on the terminal with the following commands:=

- make clean
- make
- make run

	```Java
	default:
		javac *.java
	run:
		java Main
	clean:
		rm -f *.class
		reset
		clear
	tar:
		tar -cvz *.java -f Code.tar.gz
	untar:
		tar -zxvf *.tar.gz
	```

---

---

<p align="center">The End, Thank You</p>

---
