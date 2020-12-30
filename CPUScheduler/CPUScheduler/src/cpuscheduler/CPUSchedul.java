/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuscheduler;
/**
 *
 * @author MARIAM
 */
public class CPUSchedul {

    //creating variables
    private int n, A[], Bu[], P[];
    private float Twt, Awt, Wt[], w;

    public CPUSchedul() {
        //class constructor initiaises variables
        Bu = new int[20];
        A = new int[20];
        P = new int[20];
        Wt = new float[20];
    }

    public void GetData(int[] Burst, int[] ArrT, int[] Priority, int numofrows) {    
        n = numofrows;
        for (int i = 0; i < n; i++) {
            Bu[i] = Burst[i];
            A[i] = ArrT[i];
            P[i] = Priority[i];
        }        
    }
    
    public double printTotalWaitingTime(){
        return Twt;
    }

    public double printAverageWaitingTime(){
        return Awt;
    }
    
    //Function calculates average waiting time and total waiting time for every algorithm
    public void getAverageWaitingTime() {
        Twt = 0; 
        Awt = 0;      
        int i;
        for (i = 0; i < n; i++) {
            Twt = Twt + Wt[i];
        }
        Awt = Twt / n;    
       
    }
    public void average(){
        Twt = 0; 
        Awt = 0;      
        int i;
         for( i=1; i<=n; i++){
        Twt=Twt+(Wt[i]-A[i]);}
        Awt=Twt/n;
        
        
    }
    
//Implementing the scheduling algorithm First come first serve FCFS*********
    public void FirstComeFirstServe() {
        int i;
        Wt[0] = 0;
        for (i = 1; i < n; i++) {
            Wt[i] = Bu[i - 1] + Wt[i - 1];
        }
        this.getAverageWaitingTime();
    }
    
//Implementing the scheduling algorithm Shrtest Job First -The normal version- SJF*********
    public void ShortestJobFirst() {
        int i, j, temp;
        Twt = 0;
        
        for (i = n-1; i >= 0; i--) {
            for (j = 1; j < n; j++) {
                if (Bu[j - 1] > Bu[j]) {
                    temp = Bu[j - 1];
                    Bu[j - 1] = Bu[j];
                    Bu[j] = temp;
                }
            }
        }  
        
        Wt[0] = 0;
        for (i = 1; i < n; i++) {
            Wt[i] = Bu[i - 1] + Wt[i - 1];
        }
        
        this.getAverageWaitingTime();
    }

    //Implementing the scheduling algorithm Shrtest Job First -The Preemptive version- SJFP*********
    public void ShortestJobFirstPreemptive() {
        int i=0, j=0, m, k, Tt = 0, temp;
        
        int B[] = new int[20];
        int Wtm[] = new int[20];
        
        char S[] = new char[20];
        char start[] = new char[20];
        int max = 0, Time = 0, min;
        Twt = 0;
       
        
        for (i = 1; i <= n; i++) {
            B[i]=Bu[i];
            if (Bu[i] > max) {
                max = Bu[i];
            }
            
            Wt[i] = 0;
            S[i] = 'T';
            start[i] = 'F';
            Tt = Tt + Bu[i];
            
            
            if (A[i] > Time) {
                Time = A[i];
            }
        }
        
        //cout<<"Max="<<max;
        int flag = 0, t = 0;
        w=0;
        i = 1;
        while (t < Time) {
            if (A[i] <= t && Bu[i] != 0) {
                if (flag == 0) {
                    Wt[i] = Wt[i] + w;
                }
                
                Bu[i] = Bu[i] - 1;
                if (Bu[i] == 0) {
                    S[i] = 'F';
                }
                
                start[i] = 'T';
                t++;
                w = w + 1;
                if (S[i] != 'F') {
                    j = 1;
                    flag = 1;
                    while (j <= n && flag != 0) {
                        if (S[j] != 'F' && Bu[i] > Bu[j] && A[j] <= t && i != j) {
                            flag = 0;
                            Wt[i] = Wt[i] - w;
                            i = j;
                        } else {
                            flag = 1;
                        }
                        j++;
                    }
                } else {
                    i++;
                    j = 1;
                    while (A[j] <= t && j <= n) {
                        if (Bu[i] > Bu[j] && S[j] != 'F') {
                            flag = 0;
                            i = j;
                        }
                        j++;
                    }
                }
            } else if (flag == 0) {
                i++;
            }
        }

        while (w < Tt) {
            min = max + 1;
            i = 1;
            while (i <= n) {
                if (min > Bu[i] && S[i] == 'T') {
                    min = Bu[i];
                    j = i;
                }
                i++;
            }
            i = j;
            if (w == Time && start[i] == 'T') {
                w = w + Bu[i];
                S[i] = 'F';
            } else {
                Wt[i] = Wt[i] + w;
                w = w + Bu[i];
                S[i] = 'F';
            }
        }

        for (i = 1; i <= n; i++) {
            Wt[i] = Wt[i] - A[i];
        }
        this.getAverageWaitingTime();
    }

    //Implementing the scheduling algorithm Shrtest Job First -The Nonpreemptive version- SJFN*********
    public void ShortestJobFirstNonPreemptive() {
        int i=0, Tt = 0, temp, j=0 ;
        char S[] = new char[20];
        int  temp1 ;
        float t;
        w = 0;
        
        for (i = 0; i < n; i++) {
            S[i] = 'T';
            Tt = Tt + Bu[i];
        }

        for (i = n; i >= 0; i--) {
            for (j = 3; j < n; j++) {
                if (Bu[j - 1] > Bu[j]) {
                    temp = Bu[j - 1];
                    temp1 = A[j - 1];
                    Bu[j - 1] = Bu[j];
                    A[j - 1] = A[j];
                    Bu[j] = temp;
                    A[j] = temp1;
                }
            }
        }

        //For the 1st process
        Wt[0] = 0;
        w = w + Bu[0];
        t = w;
        S[0] = 'F';

        while (w < Tt) {
            i = 2;
            while (i < n) {
                if (S[i] == 'T' && A[i] <= t) {
                    Wt[i] = w;
                    S[i] = 'F';
                    w = w + Bu[i];
                    t = w;
                    i = 1;
                } else {
                    i++;
                }
            }
        }
        this.average();
    }

    public void Priority() {
        int i, j;
        w = 0;
        int max;
        Twt = 0;
        max = 1;
        
        for (i = 0; i < n; i++) {
            if (max < P[i]) {
                max = P[i];
            }
        }
        j = 0;
        while (j <= max) {
            i = 0;
            while (i < n) {
                if (P[i] == j) {
                    Wt[i] = w;
                    w = w + Bu[i];
                }
                i++;
            }
            j++;
        }
        this.getAverageWaitingTime();
    }

    public void RoundRobin(int tq) {
        int i, j, k;
        int Rrobin[][] = new int[20][20];
        int B[] = new int[20];
        int count[] = new int[20];
        int max = 0;
        int m;
        Twt = 0;

        for (i = 0; i < n; i++) {
            B[i]=Bu[i];
            if (max < Bu[i]) {
                max = Bu[i];
            }
            Wt[i] = 0;
        }

        //TO find the dimension of the Rrobin array
        m = max / tq + 1;

        //initializing Rrobin array
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                Rrobin[i][j] = 0;
            }
        }
        
        //placing value in the Rrobin array
        i = 0;
        while (i < n) {
            j = 0;
            while (B[i] > 0) {
                if (B[i] >= tq) {
                    B[i] = B[i] - tq;
                    Rrobin[i][j] = tq;
                    j++;
                } else {
                    Rrobin[i][j] = B[i];
                    B[i] = 0;
                    j++;
                }
            }
            count[i] = j - 1;
            i++;
        }

        //calculating weighting time
        int x = 0;
        i = 0;
        while (x < n) {
            for (int a = 0; a < x ; a++) {
                Wt[x] = Wt[x] + Rrobin[a][i];
            }
            
            i = 0;
            int z = x;
            j = count[z];
            k = 0;
            
            while (k <= j-2 ) {
                if (i == n ) {
                    i = 0;
                    k++;
                } else {
                    if (i != z) {
                        Wt[z] = Wt[z] + Rrobin[i][k];
                    }
                    i++;
                }
            }
            
            x++;
        }
        this.getAverageWaitingTime();
    }
}
