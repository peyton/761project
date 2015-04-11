import numpy
import matplotlib.pyplot as plt
import time

#plot the result
def render(x, y, fileName, setFlag = True):
    x = numpy.array(x)
    y = numpy.array(y)
    fig, ax = plt.subplots(1,1)
    #ax.set_xticks(numpy.arange(0,1,0.1))
    if setFlag:
        ax.set_xscale("log")
        ax.set_yscale("log")
        tma = max(y)
        plt.ylim(ymin=0.1, ymax=tma*10)
    ax.scatter(x,y)
    ax.grid()
    fig.show()
    fig.savefig(fileName)
#   raw_input()

def renderCompare(x, y1, y2, title, xlabel, ylabel, outName):
    print len(x)
    print len(y1)
    print len(y2)
    fig, ax = plt.subplots(1,1)
    #ax.set_xscale("log")
    ax.plot(x, y1, 'bs-', x, y2, 'g^-')
    ax.grid()
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    fig.show()
    fig.savefig(outName)


def renderCompareHist(arrT, arrF, title, xlabel, ylabel, outName):
    print "beginToRender"
    plt.hist(arrT, bins=20, histtype='step', normed=True, color='b', label='True')
    print "render1 complete"
    plt.hist(arrF, bins=20, histtype='step', normed=True, color='r', label='False')
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend()
    #plt.show()
    print "save image to file"
    plt.savefig(outName)

if __name__ == "__main__":
    pass
