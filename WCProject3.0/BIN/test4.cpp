# include<iostream>
using namespace std;

int main(int argc, char const *argv[])
{
	//this is a comment!
	if (1)
		cout<<"Begin Here."<<endl;
	cout<<"Hello world!"<<endl;
	int i = 5;
	while (i)
		i--;
	switch (i)
	{
		case 0: cout<<"i = 0."<<endl; break;
		default: break;
	}
	/*this is another comment!*/
	return 0;
}