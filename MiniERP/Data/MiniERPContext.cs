using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace MiniERP.Models
{
    public class MiniERPContext : DbContext
    {
        public MiniERPContext (DbContextOptions<MiniERPContext> options)
            : base(options)
        {
        }

        public DbSet<MiniERP.Models.Produto> Produto { get; set; }
    }
}
