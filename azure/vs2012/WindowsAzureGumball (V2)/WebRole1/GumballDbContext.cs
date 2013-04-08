using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace WebRole1
{
    public class GumballDbContext : System.Data.Entity.DbContext
    {
        public DbSet<GumballMachine> gumballMachines { get; set; }
    }
}