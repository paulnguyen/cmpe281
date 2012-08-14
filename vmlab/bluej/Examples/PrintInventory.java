package Examples;

import java.net.URL;
import com.vmware.vim25.mo.*;
import com.vmware.vim25.*;

public class PrintInventory 
{
    public static void run() throws Exception
    {

        ServiceInstance si = new ServiceInstance(
            new URL(Config.getVmwareHostURL()), Config.getVmwareLogin(), Config.getVmwarePassword(), true);
        Folder rootFolder = si.getRootFolder();
        
        System.out.println("============ Data Centers ============");
        ManagedEntity[] dcs = new InventoryNavigator(rootFolder).searchManagedEntities(
                new String[][] { {"Datacenter", "name" }, }, true);
        for(int i=0; i<dcs.length; i++)
        {
            System.out.println("Datacenter["+i+"]=" + dcs[i].getName());
        }
        
        System.out.println("\n============ Virtual Machines ============");
        ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities(
                new String[][] { {"VirtualMachine", "name" }, }, true);
        for(int i=0; i<vms.length; i++)
        {
            System.out.println("vm["+i+"]=" + vms[i].getName());
        }

        System.out.println("\n============ Hosts ============");
        ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities(
                new String[][] { {"HostSystem", "name" }, }, true);
        for(int i=0; i<hosts.length; i++)
        {
            System.out.println("host["+i+"]=" + hosts[i].getName());
        }
        
        System.out.println("\n============ Host Config Info ============");
        HostSystem host = (HostSystem) hosts[0] ;
        if ( host != null )
        {
            System.out.println("Host = " + host.getName());
            HostListSummary hls = host.getSummary();
            HostListSummaryQuickStats qstats = hls.getQuickStats();
            System.out.println("Distributed CPU Fairness = "+qstats.getDistributedCpuFairness());
            System.out.println("Distributed Memory Fairness = "+qstats.getDistributedMemoryFairness());
            System.out.println("Aggregated CPU Usage Accross All Cores (in MHz) = "+qstats.getOverallCpuUsage());
            System.out.println("Physical Memory Usage (MB) = "+qstats.getOverallMemoryUsage());
        }
     
        
        si.getServerConnection().logout();
    }

}



