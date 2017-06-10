# Fishing--一个专注于Fragment通信的框架

## 开发摘记

如果把通信比作钓鱼，如果每次都有鱼上钩，那么我想可以分为以下四种：

* 1.鱼钩上**不钩**饵料，然后抛出去，钩到鱼了，这时候鱼线断了，连线带钩和鱼都没了。
* 2.鱼钩上**钩上**饵料，然后抛出去，钩到鱼了，这时候鱼线断了，连线带钩和鱼带饵料都没了。
* 3.鱼钩上**不钩**饵料，然后抛出去，钩到鱼了，这时候鱼线**没断**，连线带钩和鱼带饵料都**拉回来**了。
* 4.鱼钩上**钩上**饵料，然后抛出去，钩到鱼了，这时候鱼线**没断**，连线带钩和鱼带饵料都**拉回来**了。

其实这四种就可以对应着四种不同的通信：

* 1.A向发了一个**空的通知**让B响应了，A不需要知道B是否响应(鱼线断了)。
* 2.A向发了一个**携带参数的通知**让B响应了，A不需要知道B是否响应(鱼线断了)。
* 3.A向发了一个**空的通知**让B响应了，A需要知道B是否响应，最终A得到了B响应结果(鱼线完好，成功回收)。
* 4.A向发了一个**携带参数的通知**让B响应了，A需要知道B是否响应，最终A得到了B响应结果(鱼线完好，成功回收)。

再说的简单一点，那就是：

* 1.调用“void func()”方法，无参无返回值。
* 2.调用“void func(P p)”方法，有参无返回值。
* 3.调用“R func()”方法，无参有返回值。
* 4.调用“R func(P p)”方法，有参有返回值。

那么基于上述，应该设计如下：

四种容器分别是（ArrayMap）：
1.无参数无返回
2.有参数无返回
3.无参数有返回
4.有参数有返回

## 必备知识

* 面向对象。

## 应用场景

* Fragment、Activity通信

## License

    Copyright 2017, Halohoop

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.