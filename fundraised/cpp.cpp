#include <stdio.h>
#include <string.h>
#include <algorithm>
using namespace std;

__int64 dp[10005];
__int64 val[105],wei[105];

int main()
{
    __int64 t,i,j;
    scanf("%I64d",&t);
    while(t--)
    {
        __int64 n,x;
        scanf("%I64d%I64d",&n,&x);
        for(i = 1;i<=n;i++)
        scanf("%I64d",&val[i]);
        for(i = 1;i<=n;i++)
        scanf("%I64d",&wei[i]);
        memset(dp,0,sizeof(dp));
        for(i = 1;i<=n;i++)
        {
            for(j = wei[i];j<=x;j++)
            {
                dp[j] = max(dp[j],dp[j-wei[i]]+val[i]);
            }
        }
        printf("%I64d\n",dp[x]);
    }

    return 0;
} 
