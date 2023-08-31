import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
public class Bakery implements Lock
{
	private volatile boolean []  flag;
	private volatile int [] label;
	private int n;
	public Bakery(int n)
	{
		this.n=n;
		flag= new boolean[n];
		label=new int[n];
		for(int i = 0;i < n; ++i)
		{
			flag[i] = false;
			label[i] = 0;
		}
	}
	@Override
	public void lock()
	{
		int i = filterThread(Thread.currentThread().getName());
		flag[i] = true;
		label[i] = max(label);
		for(int k = 0;k < n; k++)
		{
			while((k!=i) && (flag[k] && label[k]<label[i]))
			{
				//spin
			}
		}
	}
	public void lockInterruptibly() throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}
	public boolean tryLock()
	{
		throw new UnsupportedOperationException();
	}
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public void unlock()
	{
		flag[filterThread(Thread.currentThread().getName())]=false;
	}
	public Condition newCondition()
	{
		throw new UnsupportedOperationException();
	}
	public int filterThread(String thread)
	{
		return Character.getNumericValue(thread.charAt(7));
	}
	public int max(int [] arr)
	{
		int maxValue=Integer.MIN_VALUE;
		for(int i = 0;i < arr.length;++i)
		{
			if(arr[i] > maxValue)
				maxValue = arr[i];
		}
		return maxValue+1;
	}
}