# include<stdio.h>

int main(int argc, char const *argv[])
{
	//this is a comment!
	if (1)
		printf("Begin Here.\n");
	printf("Hello world!\n");
	int i = 5;
	while (i)
		i--;
	switch (i)
	{
		case 0: print("i = 0.\n"); break;
		default: break;
	}
	for (int i = 1; i < 5; i++)
	{
		printf("%d", i);
	}
	/*this is another comment!*/
	return 0;
}