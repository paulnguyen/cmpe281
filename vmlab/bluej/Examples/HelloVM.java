package Examples;

import java.net.URL;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;


public class HelloVM
{

    public static void run() throws Exception
    {

        ServiceInstance si = new ServiceInstance(
            new URL(Config.getVmwareHostURL()), Config.getVmwareLogin(), Config.getVmwarePassword(), true);
        
        Folder rootFolder = si.getRootFolder();
        String name = rootFolder.getName();
        System.out.println("root:" + name);
        ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
        if(mes==null || mes.length ==0)
        {
            return;
        }
        VirtualMachine vm = (VirtualMachine) mes[0]; 
        
        System.out.println("\n============ Virtual Machine Info ============");
        if ( vm != null )
        {
            VirtualMachineConfigInfo vminfo = vm.getConfig();
            VirtualMachineCapability vmc = vm.getCapability();
            System.out.println("Hello " + vm.getName());
            System.out.println("GuestOS: " + vminfo.getGuestFullName());
            System.out.println("Multiple snapshot supported: " + vmc.isMultipleSnapshotsSupported());
           VirtualMachineSummary vmsum = vm.getSummary() ;
           VirtualMachineQuickStats vmstats = vmsum.getQuickStats() ;
           System.out.printf( "Guest Memory Usage: %d MB\n", vmstats.getGuestMemoryUsage() ) ;
           System.out.printf( "Host Memory Usage: %d MB\n", vmstats.getHostMemoryUsage() ) ;
           System.out.printf( "Overall CPU Usage: %d MHz\n", vmstats.getOverallCpuUsage() ) ;
        }           
        
        si.getServerConnection().logout();
    }

}
