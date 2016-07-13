package ui;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class code {
	public final static int maxn=110;        //最大顶点个数
	public final static int INF= 100;    //权值上限
	public final static double t=0.85;
	public final static int k=1;
	public static int count=-1;
	public static double max=0;
	public static int predict=-1;
	public static char R;
	public static char V;
	public static double LEV,LEV2;
	public static char candidate[];
	public static int adjmatrix[][];
	public static char promatrix[][];
	public static int readjmatrix[][];
	public static char repromatrix[][];
	public static int readjmatrix1[][];
	public static char repromatrix1[][];
	
	//获取输入
	public static char Getinfo(){
		System.out.println("需要移除的节点：");
		Scanner sc = new Scanner(System.in);
		R = sc.next().charAt(0);
		sc.close();
		return R;
	}

	//输出结果
	public static void Result(){
		String e="       WRP      rv(v,r)    p\n------------------------------------";
	    System.out.println(e);
		for(int i=0;i<count;i++)
	        if(i!=(R-65)){
	            String c="  "+(char)(i+65)+"   "
	                    +String.format("%.3f",WRP((char)(i+65),R,count))+"     "
	                    +String.format("%.3f",ReplacementValue((char)(i+65),R,count))+"    "
	                    +String.format("%.3f",ReplaceProbability((char)(i+65),R,count));
	            System.out.println(c);
	        }
	    System.out.println("------------------------------------");

	    if(predict>-1){
	        String f="By TSP, vertex "+ (char)(predict+65)
	                +" is the best candidate \nwhich means it is most likely to replace vertex "+R+" .";

	        System.out.println(f);
	        
	    }
	    else{
	        String f="By TSP, there is no candidate vertex to replace vertex "+R+" .";

	        System.out.println(f);
	   
	    }
	}
	
	//读取整理adj矩阵
	public static void adjfile(String filepath){
		try {
            String encoding="GBK";
            File file=new File(filepath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	char NodeA = lineTxt.charAt(0);
                	char NodeB = lineTxt.charAt(2);
                	
                	int row = NodeA-65;
                	int column = NodeB-65;
                	
                	if(row>count)
                        count=row;
                    if(column>count)
                        count=column;
                }
                count+=1;   // 计算节点个数
                System.out.println("node number"+count);                
                initialize_matrix(count);   // 初始化二维矩阵
                read.close();
            }else
            	System.out.println("找不到指定的文件");
        } catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
        }
		
		try {
            String encoding="GBK";
            File file=new File(filepath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	char NodeA = lineTxt.charAt(0);
                	char NodeB = lineTxt.charAt(2);
                	
                	int row = NodeA-65;
                	int column = NodeB-65;
                	
                	adjmatrix[row][column] = 1;
                    adjmatrix[row][column] = 1;
                    adjmatrix[column][row] = 1; 	//将有边的两点赋值为1，建立邻接矩阵
                    adjmatrix[column][row] = 1;
                }
                read.close();
            }else
            	System.out.println("找不到指定的文件");
        } catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
        }
		
		try {
            FileWriter fw = new FileWriter("/Users/gaoyile/java/settled.txt",false);
            String num="*ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            fw.write("The Adjacency Matrix is : \n");
            for(int i=0;i<=count;i++)
                fw.write(num.substring(i,i+1)+" ");
            fw.write('\n');
            
            for(int i=0;i<count;i++){
                fw.write(num.substring(i+1,i+2)+" ");
                for(int j=0;j<count;j++){
                    if(adjmatrix[i][j]==1)
                        fw.write("1 "); 
                    else
                    	fw.write("0 ");
                }
                fw.write("\n");
            }
            fw.write("\n\n\n");
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("写入失败");
            System.exit(-1);
        }
		
	}
	
	//读取整理pro矩阵
	public static void profile(String filepath){
		try {
            String encoding="GBK";
            File file=new File(filepath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	char Node = lineTxt.charAt(0);
                	int Weight = lineTxt.charAt(2)-48;
                	char Role = lineTxt.charAt(4);
                	int Capacity = lineTxt.charAt(6)-48;  //容量
                    int Violence = lineTxt.charAt(8);  //2代表暴力，1代表中立，0代表反暴力
                    int Degree = 0;
                    
                    int row = Node - 65;
                    for(int i=0;i<count;i++){
                        if(adjmatrix[row][i]==1)
                            Degree += 1;
                    }
                    promatrix[row][0] = Node;
                    promatrix[row][1] = lineTxt.charAt(2); 	//权重列
                    promatrix[row][2] = Role;     //角色列
                    promatrix[row][3] = lineTxt.charAt(6); //容量列
                    promatrix[row][4] = lineTxt.charAt(8);  //暴力属性列
                    promatrix[row][5] = (char)(Degree+48);   //度列
                	
                }
                read.close();
            }else
            	System.out.println("找不到指定的文件");
        } catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
        }
		
		try{
			FileWriter fw = new FileWriter("/Users/gaoyile/java/settled.txt",true);
			fw.write("The Property Matrix is :\n");
		    fw.write("Node    Weight    Role     Capacity Violence  Degree\n");
		    
		    for(int i=0;i<count;i++){
		        for(int j=0;j<count;j++){
		            String b=String.valueOf(promatrix[i][j]);
		            fw.write("  "+b+"      ");
		        }
		        fw.write("\n");
		    }
		    fw.write("\n\n");
		    fw.close();    
		} catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("写入失败");
            System.exit(-1);
        }
	}
	
	//初始化矩阵
	public static void initialize_matrix(int count){
	    candidate= new char[count];
		adjmatrix = new int[count][count];
	    promatrix = new char[count][count];
	    readjmatrix = new int[count-1][count-1];
	    repromatrix= new char[count-1][6];
	    readjmatrix1 = new int[count-1][count-1];
	    repromatrix1= new char[count-1][6];
	    
	    for(int i=0;i<count;i++)
	        for(int j=0;j<count;j++)
	            adjmatrix[i][j]=0;
	    for(int i=0;i<count;i++)
	        for(int j=0;j<count;j++)
	            promatrix[i][j]=' ';

	    for(int i=0;i<count;i++)
	        candidate[i]=0;

	    for(int i=0;i<count-1;i++)
	        for(int j=0;j<count-1;j++)
	            readjmatrix[i][j]=0;
	    for(int i=0;i<count-1;i++)
	        for(int j=0;j<count-1;j++)
	            repromatrix[i][j] = ' ';
	    
	    for(int i=0;i<count-1;i++)
	        for(int j=0;j<count-1;j++)
	            readjmatrix1[i][j]=0;
	    for(int i=0;i<count-1;i++)
	        for(int j=0;j<count-1;j++)
	            repromatrix1[i][j] = ' ';
	}
	
	//计算节点的度数
	public static int NeighbourNumber(char v, char r, int count){
	    int tmp=0;
	    for(int j=0;j<count;j++){
	        if(adjmatrix[j][v-65]==1&&j!=(r-65))
	            tmp+=1;
	    }
	    return tmp;
	}

	//计算WRP（weighted removal pagerank）
	public static double WRP(char v, char r, int count){
	    int totalweight=0;
	    double wrp=0;
	    int singleweight=promatrix[v-65][1]-48; // weight of v

	    for(int i=0;i<count;i++)
	        totalweight+=(promatrix[i][1]-48);  //total weight of all vertices

	    totalweight-=(promatrix[r-65][1]-48); //total weight of all vertices except r

	    wrp+=(1-t)*singleweight/totalweight;

	    for(int j=0;j<count;j++){
	        if(j!=(r-65)&&adjmatrix[v-65][j]==1){
	            char k=(char)(65+j);
	            wrp+=t/((count-1)*NeighbourNumber(k,r,count));
	        }
	    }
	    return wrp;
	}

	//计算replacement value
	public static double ReplacementValue(char v,char r,int count){
	    double rv=0;
	    double a[]={0.8,0.2};

	    rv = a[0]*WRP(v,r,count);

	    for(int i=1;i<2;i++){
	        rv += a[i]*(promatrix[v-65][1]-48);
	    }

	    return rv;
	}

	//匿名Comparator实现
    public static Comparator<node> idComparator = new Comparator<node>(){ 

        @Override
        public int compare(node c1, node c2) {
            return (int) (c1.id - c2.id);
        }
    };
    
    //Dijkstra算法，传入源顶点
	public static int Dijkstra(char v,char r,int n)        
	{
	    int parent[]=new int[maxn];           //每个顶点的父亲节点，可以用于还原最短路径树
	    boolean visited[]= new boolean[maxn];         //用于判断顶点是否已经在最短路径树中，或者说是否已找到最短路径
	    node d[]=new node[maxn];               //源点到每个顶点估算距离，最后结果为源点到所有顶点的最短路。
	    Queue<node> q = new PriorityQueue<>(n,idComparator);
	    for(int i=0;i<maxn;i++)
	    	d[i]=new node();
	    
	    for(int i = 1; i <= n; i++)     //初始化
	    {
	        d[i].id = i;
	        d[i].weight = INF;          //估算距离置INF
	        parent[i] = -1;             //每个顶点都无父亲节点
	        visited[i] = false;
	     }

	     d[v-64].weight = 0;                //源点到源点最短路权值为0
	     q.add(d[v-64]);                   //压入队列中
	     while(q.peek()!=null)               //算法的核心，队列空说明完成了操作
	     {
	         node cd =q.peek();
	         q.poll();

	         int u = cd.id;

	         if(visited[u])
	             continue;

	         visited[u] = true;

	         //松弛操作

	         for(int j = 1; j <= n; j++) //找所有与他相邻的顶点，进行松弛操作，更新估算距离，压入队列。
	         {
	             if(j != u && !visited[j] && d[j].weight > d[u].weight+adjmatrix[u-1][j-1]&&adjmatrix[u-1][j-1]!=0)
	             {
	                d[j].weight = d[u].weight+adjmatrix[u-1][j-1];
	                parent[j] = u;
	                q.add(d[j]);
	            }
	        }
	     }
	     return d[r-64].weight;
	}
	
	//POCC值计算
	public static double POCC(int count){
	    double pocc=0;
	    int a[]=new int[count];

	    for(int i = 0;i < count;i++)
	        a[i] = 0;

	    for(int i=0;i<count;i++){ //i为目标点
	        double num1=0,num2=0;
	        for(int j=0;j<count;j++){ //其余点到i的距离
	            if((Dijkstra((char)(65+j),(char)(65+i),count)<=k)&&(j!=i)){
	                a[j]+=1;
	                num1+=1;
	            }
	            
	        }

	        for(int p=0;p<count-1;p++){
	            for(int m=p+1;m<count;m++){
	                if((a[p]==1)&&(a[m]==1)&&(adjmatrix[p][m]==1))
	                    num2+=1;
	            }
	        }
	        try{
				FileWriter fw = new FileWriter("/Users/gaoyile/java/settled.txt",true);
				if(num2<=1){
		            String tmp = "POCC of Vertix "+(char)(65+i)+" is: 0";
		            System.out.println(tmp);
		            fw.write(tmp+'\n');
		        }
		        else{
		            pocc=2*num2/(num1*(num1-1));
		            String tmp="POCC of Vertix "+(char)(65+i)+" is: "+ String.format("%.4f", pocc);
		            System.out.println(tmp);
		            fw.write(tmp+'\n');
		        }
				fw.write('\n');
			    fw.close();
			    
			} catch (IOException e1) {
	            e1.printStackTrace();
	            System.out.println("写入失败");
	            System.exit(-1);
	        }
	        

	        for(int l=0;l<count;l++)
	            a[l]=0;
	    }
	    return pocc;
	}
	
	//计算候补集
	public static void Candidate(char r,int k,int count){
	    boolean flag=false;
	    for(int i = 0;i < count;i++)
	        candidate[i] = 0;

	    for(int i=0;i<count;i++){
	        if(i!=(r-65))
	            if(promatrix[i][1]<=promatrix[r-65][1])
	                if(Dijkstra((char)(i+65),r,count)<=k)
	                    if(WRP((char)(i+65),r,count)>=0){                        
	                        candidate[i]=(char)(i+65);
	                        flag=true;
	                    }
	    }

	    try{
			FileWriter fw = new FileWriter("/Users/gaoyile/java/re-info.txt",false);
			if(!flag){
		        String a = "There is no candidate for the node "+r;
		        System.out.println(a);
		        fw.write(a);
		    }
		    else{
		        String a = "The Candidate Set of node "+r+" is";
		        System.out.println(a);
		        fw.write(a+'\n');
		        String b = "";
		        for(int i=0;i<count;i++)
		            if(candidate[i]!=(char)(0))
		                b = b + candidate[i] + " ";
		        System.out.println(b);
		        fw.write(b);
		    }
			fw.write('\n');
		    fw.close();
		    
		} catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("写入失败");
            System.exit(-1);
        }
	    
	    
	}
	
	//替补概率
	public static double ReplaceProbability(char v,char r,int count){
	    double p=0,tmp=0;

	    for(int i=0;i<count;i++){
	        if(candidate[i]==(char)(i+65))
	            tmp+=ReplacementValue((char)(i+65),r,count);
	    }

	    if(candidate[v-65]!=(char)(0)){
	        p=ReplacementValue(v,r,count)/tmp;
	        if(p>max){
	            predict=(v-65);
	            max=p;
	        }
	    }    
	    else
	        p=0;
	    
	    V=(char)(predict+65);
	    return p;
	}
	
	//原网络杀伤力1
	public static double Lethality1(int count){
	    double L1 = 0;

	    for(int i=0;i<count;i++){
	        if(promatrix[i][4]-48==2)
	            L1 += (promatrix[i][1]-48);
	        if(promatrix[i][4]-48==0)
	            L1 -= (promatrix[i][1]-48);
	    }
	    return L1;
	}

	//原网络杀伤力2
	public static double Lethality2(int count){
	    double L2 = 0;

	    for(int i=0;i<count;i++){
	        if(promatrix[i][4]-48==2)
	            L2 += (promatrix[i][1]-48)*(promatrix[i][5]-48);
	        if(promatrix[i][4]-48==0)
	            L2 -= (promatrix[i][1]-48)*(promatrix[i][5]-48);
	    }
	    return L2;
	}

	//现网络杀伤力1
	public static double reLethality1(int count){
	    double L1 = 0;

	    for(int i=0;i<count-1;i++){
	        if(repromatrix[i][4]-48==2)
	            L1 += (repromatrix[i][1]-48);
	        if(repromatrix[i][4]-48==0)
	            L1 -= (repromatrix[i][1]-48);
	    }
	    return L1;
	}

	//现网络杀伤力2
	public static double reLethality2(int count){
	    double L2 = 0;

	    for(int i=0;i<count-1;i++){
	        if(repromatrix[i][4]-48==2)
	            L2 += (repromatrix[i][1]-48)*(repromatrix[i][5]-48);
	        if(repromatrix[i][4]-48==0)
	            L2 -= (repromatrix[i][1]-48)*(repromatrix[i][5]-48);
	    }

	    return L2;
	}

	//现网络杀伤力11
	public static double reLethality11(int count){
	    double L1 = 0;

	    for(int i=0;i<count-1;i++){
	        if(repromatrix1[i][4]-48==2)
	            L1 += (repromatrix1[i][1]-48);
	        if(repromatrix1[i][4]-48==0)
	            L1 -= (repromatrix1[i][1]-48);
	    }

	    return L1;
	}

	//现网络杀伤力21
	public static double reLethality21(int count){
	    double L2 = 0;

	    for(int i=0;i<count-1;i++){
	        if(repromatrix1[i][4]-48==2)
	            L2 += (repromatrix1[i][1]-48)*(repromatrix1[i][5]-48);
	        if(repromatrix1[i][4]-48==0)
	            L2 -= (repromatrix1[i][1]-48)*(repromatrix1[i][5]-48);
	    }

	    return L2;
	}
	
	//网络重塑后的adj矩阵
	public static void ReshapeAdj(char v,char r,int count){
	    for(int i=0;i<count-1;i++)
	        for(int j=0;j<count-1;j++)
	            readjmatrix[i][j]=0;

	    for(int i=0;i<count;i++){
	        for(int j=0;j<count;j++){
	            if(i<(r-65)&&j<(r-65))
	                readjmatrix[i][j]=adjmatrix[i][j];
	            if(i<(r-65)&&j>(r-65))
	                readjmatrix[i][j-1]=adjmatrix[i][j];
	            if(i>(r-65)&&j<(r-65))
	                readjmatrix[i-1][j]=adjmatrix[i][j];
	            if(i>(r-65)&&j>(r-65))
	                readjmatrix[i-1][j-1]=adjmatrix[i][j];
	        }
	    }

	    for(int i=0;i<count;i++){
	        if(adjmatrix[i][r-65]==1&&v<r&&i<(r-65)&&i!=(v-65)){
	            readjmatrix[i][v-65]=1;
	            readjmatrix[v-65][i]=1;
	        }
	        if(adjmatrix[i][r-65]==1&&v<r&&i>(r-65)&&i!=(v-65)){
	            readjmatrix[i-1][v-65]=1;
	            readjmatrix[v-65][i-1]=1;
	        }
	        if(adjmatrix[i][r-65]==1&&v>r&&i<(r-65)&&i!=(v-65)){
	            readjmatrix[i][v-66]=1;
	            readjmatrix[v-66][i]=1;
	        }
	        if(adjmatrix[i][r-65]==1&&v>r&&i>(r-65)&&i!=(v-65)){
	            readjmatrix[i-1][v-66]=1;
	            readjmatrix[v-66][i-1]=1;
	        }
	    }
	    
	    try {
            FileWriter fw = new FileWriter("/Users/gaoyile/java/reshapefile.txt",false);
            String num="*ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            fw.write("The Adjacency Matrix when vertex "+r+" is replaced by vertex "+v+" is: \n");
            fw.write("* ");
            
    	    for(int i=0;i<count-1;i++){
    	        if(i<(r-65))
    	            fw.write(num.substring(i+1,i+2)+" ");
    	        else
    	        	fw.write(num.substring(i+2,i+3)+" ");
    	    }
    	    fw.write("\n");
    	    
    	    for(int k=0;k<count-1;k++){
    	        if(k<(r-65))
    	        	fw.write(num.substring(k+1,k+2)+" ");
    	        else
    	        	fw.write(num.substring(k+2,k+3)+" ");
    	        for(int m=0;m<count-1;m++)
    	        	fw.write(readjmatrix[k][m]+" ");
    	        fw.write("\n");
    	    }
    	    
            fw.write("\n\n\n");
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("写入失败");
            System.exit(-1);
        }
	}

	//网络重塑后的pro矩阵
	public static void ReshapePro(char v,char r,int count){

	    for(int i=0;i<count-1;i++)
	        for(int j=0;j<6;j++)
	            repromatrix[i][j] = ' ';

	    for(int i=0;i<count;i++){
	        for(int j=0;j<6;j++){
	            if(i<(r-65))
	                repromatrix[i][j]=promatrix[i][j];
	            if(i>(r-65))
	                repromatrix[i-1][j]=promatrix[i][j];
	        }
	        if(i==(v-65)&&i<(r-65))
	            repromatrix[i][1]=promatrix[r-65][1];
	        if(i==(v-65)&&i>(r-65))
	            repromatrix[i-1][1]=promatrix[r-65][1];
	    }

	    for(int i=0;i<count-1;i++){
	        int tmp=0;
	        for(int j=0;j<count-1;j++)
	            if(readjmatrix[i][j]==1)
	                tmp+=1;
	        repromatrix[i][5]=(char) (tmp+48);
	    }

	    try {
            FileWriter fw = new FileWriter("/Users/gaoyile/java/reshapefile.txt",true);

            fw.write("The Property Matrix when vertex	"+r+" is replaced by vertex "+v+" is:\n\n");
            fw.write("N W R C V D\n");
            
            for(int i=0;i<count-1;i++){
    	        for(int j=0;j<6;j++)
    	            fw.write(repromatrix[i][j]+" ");
    	        fw.write("\n");
    	    }
    	    
            fw.write("\n\n\n");
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("写入失败");
            System.exit(-1);
        }
	    
	    
	}

	public static void PossibleWorld(int count){
	    String w="       LEV1          LEV2        \n---------------------------------";
	    System.out.println(w);
	    try {
	    FileWriter fw = new FileWriter("/Users/gaoyile/java/possibleworld.txt",false);
		fw.write(w+'\n');
		

	    for(int a=0;a<count;a++){
	        LEV=0;
	        LEV2=0;
	        for(int i = 0;i < count;i++)
	            candidate[i] = 0;
	        boolean flag=false;
	        for(int i=0;i<count;i++)
	            if(i!=a)
	                if(promatrix[i][1]<=promatrix[a][1])
	                    if(Dijkstra((char)(i+65),(char)(a+65),count)<=k)
	                        if(WRP((char)(i+65),(char)(a+65),count)>=0){
	                            candidate[i]=(char)(i+65);
	                            flag=true;
	                        }
	        
	        if(!flag){
	            String b= (char)(a+65)+"        no possible world";
	            System.out.println(b);
	            fw.write(b);
	        }
	//
	        else{            
	            for(int v=0;v<count;v++){
	                if(candidate[v]!=(char)(0)){
	                    for(int z=0;z<count-1;z++)
	                        for(int x=0;x<count-1;x++)
	                            readjmatrix1[z][x]=0;

	                    for(int z=0;z<count-1;z++)
	                        for(int x=0;x<count-1;x++)
	                            repromatrix1[z][x] = ' ';
	//*/
	                    for(int z=0;z<count;z++){
	                        for(int x=0;x<count;x++){
	                            if(z<a&&x<a)
	                                readjmatrix1[z][x]=adjmatrix[z][x];
	                            if(z<a&&x>a)
	                                readjmatrix1[z][x-1]=adjmatrix[z][x];
	                            if(z>a&&x<a)
	                                readjmatrix1[z-1][x]=adjmatrix[z][x];
	                            if(z>a&&x>a)
	                                readjmatrix1[z-1][x-1]=adjmatrix[z][x];
	                        }
	                    }

	                    for(int z=0;z<count;z++){
	                        if(adjmatrix[z][a]==1&&v<a&&z<a&&z!=(v)){
	                            readjmatrix1[z][v]=1;
	                            readjmatrix1[v][z]=1;
	                        }
	                        if(adjmatrix[z][a]==1&&v<a&&z>a&&z!=(v)){
	                            readjmatrix1[z-1][v]=1;
	                            readjmatrix1[v][z-1]=1;
	                        }
	                        if(adjmatrix[z][a]==1&&v>a&&z<a&&z!=(v)){
	                            readjmatrix1[z][v-1]=1;
	                            readjmatrix1[v-1][z]=1;
	                        }
	                        if(adjmatrix[z][a]==1&&v>a&&z>a&&z!=(v)){
	                            readjmatrix1[z-1][v-1]=1;
	                            readjmatrix1[v-1][z-1]=1;
	                        }
	                    }

	                    for(int z=0;z<count;z++){
	                        for(int x=0;x<count;x++){
	                            if(z<a)
	                                repromatrix1[z][x]=promatrix[z][x];
	                            if(z>a)
	                                repromatrix1[z-1][x]=promatrix[z][x];
	                        }
	                        if(z==(v)&&z<a)
	                            repromatrix1[z][1]=promatrix[a][1];
	                        if(z==(v)&&z>a)
	                            repromatrix1[z-1][1]=promatrix[a][1];
	                    }

	                    for(int z=0;z<count-1;z++){
	                        int tmp=0;
	                        for(int x=0;x<count-1;x++)
	                            if(readjmatrix1[z][x]==1)
	                                tmp+=1;
	                        repromatrix1[z][5]=(char) (tmp+48);
	                    }
	                }
	                
	                LEV+=ReplaceProbability((char)(v+65),(char)(a+65),count)*reLethality11(count);
	                LEV2+=ReplaceProbability((char)(v+65),(char)(a+65),count)*reLethality21(count);
	            }
	            String b= (char)(a+65)+"       "+String.format("%.0f",LEV)+"            "+String.format("%.0f",LEV2);
	            System.out.println(b);
	            fw.write(b);
	        }
	        System.out.println("");
	        fw.write("\n");
	    }
	    fw.close();
	    } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("写入失败");
            System.exit(-1);
        }
	}
}

class node{
	public int id, weight;     //源顶点id和估算距离
    
}