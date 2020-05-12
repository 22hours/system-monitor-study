using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;

namespace System_Monitor
{
    public partial class Service1 : ServiceBase
    {
        public Service1()
        {
            InitializeComponent();
        }

        protected override void OnStart(string[] args)
        {
            System.Diagnostics.Process ps = new System.Diagnostics.Process();
            ps.StartInfo.FileName = "SystemMonitor.exe";
            ps.StartInfo.WorkingDirectory= "C:\\Users\\damin\\source\\repos\\System_Monitor\\System_Monitor\\bin\\Debug";
            ps.Start();
            ps.WaitForExit(1000);
        }

        protected override void OnStop()
        {
        }
    }
}
