```c
/*栈 顺序存储结构实现*/
#include<stdio.h>

//定义常量 存储空间的初始化分配
#define MAXSIZE 20
#define ERROR 0
#define OK 1
//用typedef定义类型
typedef int Status;
typedef int SElemType;
//定义一个结构体类型
typedef struct{
    SElemType data[MAXSIZE];
    int top;
} Stack; 
//入栈 push, 将元素e为新的栈顶元素 
Status push(Stack *s,SElemType e){
	// 判断栈是否满
	if(s->top == MAXSIZE -1){
		return ERROR;
	}
	s->top++;
	s->data[s->top]=e;
	return OK;
}
//出栈 pop 将要出栈的元素保存在e中 
Status pop(Stack *s, SElemType *e){
    //判断栈是否为空 
    if(s->top == -1){
        return ERROR;
    } 
    *e = s->data[s->top];   //保存要出栈的元素 
    s->top--;   //栈顶下移 
    return OK; 
}
//初始化
Status initStack(Stack *s){
	s->top=-1;
	return OK;
}
//输出栈中的所有元素
void stackTraverse(Stack s){
	if(s.top==-1){
		 printf("栈中无元素\n");
	}else{
		while(s.top!=-1){
			printf("%d ",s.data[s.top]);
			s.top--;
		}
		printf("\n");
	}
}
int main(void){
    Stack s;
    SElemType e;    
    initStack(&s);
    int option = 1, value;
    printf("\n1.初始化栈 \n2.遍历栈 \n3.入栈 \n4.出栈 \n0.退出\n");
    while(option){
        scanf("%d",&option);
        switch(option){
            case 1:
                initStack(&s);
                break;
            case 2:
                 stackTraverse(s);
                 break;
            case 3:
                printf("请输入要插入的元素 \n");
                scanf("%d",&e);
                push(&s, e);
                stackTraverse(s);
                break;
            case 4:
                value = pop(&s, &e);
                if(value == 0){
                    printf("没有可用的元素可以出栈\n");
                }else{
                    printf("出栈的元素是:%d\n",e);
                } 
                break;
            case 0:
                return OK;
        }
    }   
    return OK;
}
```