using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebRole1
{
    public class GumballMachineRepository
    {
        GumballDbContext _Context = new GumballDbContext();

        public List<GumballMachine> getGumballMachines()
        {
            return (from e in _Context.gumballMachines
                    select e).ToList();
        }

        public GumballMachine getBySerialNumber(string serialNum)
        {
            return (from e in _Context.gumballMachines
                    where e.serialNumber == serialNum
                    select e).SingleOrDefault();
        }

        public void SaveChanges()
        {
            _Context.SaveChanges() ;
        }
    }
}