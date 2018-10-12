## 区间图着色问题 
有n个活动，对于其中的每个活动Ai均有一个开始时间Si和结束时间Fi表示该活动的举办时间是[ Si, Fi ), 其中0 <= Si < Fi < MAX。
现在我们希望使用尽可能少的教室来调度所有的活动。注意每个活动在其举办时间内都独占公共的资源（比如教室等），所以一个教室同一时间只能有一个活动。
贪心的解法为，采用2个set放置空闲教室(freeset)和占用教室(busyset)  
将起始和结束时间混合进行升序排序，★★★如果时间相同，以END优先★★★  
遍历这个2*N的有序集合，  
如果是开始，若freeset非空，则从freeset的头部获得一个教室，放入busyset；若freeset为空，则申请一个新的教室，  
将新教室直接放入busy，记录time的教室，与此同时，★★★也需要记录此index下的end类型的time的教室★★★，否则只记录了一半  
如果是结束，则直接将busyset的教室移除到freeset头部  