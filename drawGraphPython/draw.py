with open('latency.txt') as file:
    array1 = []
    array2 = []
    for line in file:
        splitted = line.split()
        array1.append(splitted[0])
        array2.append(splitted[1])

import matplotlib.pyplot as plt

plt.plot(array1,array2)

plt.xlabel('threads')
plt.ylabel('seconds')
plt.grid(True)
plt.savefig("latency.png")
plt.show()