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
            /*System.Diagnostics.Process ps = new System.Diagnostics.Process();
            ps.StartInfo.FileName = "SystemMonitor.exe";
            ps.StartInfo.WorkingDirectory= "C:\\Users\\damin\\source\\repos\\System_Monitor\\System_Monitor\\bin\\Release";
            ps.StartInfo.WindowStyle = System.Diagnostics.ProcessWindowStyle.Normal;
            ps.Start();
            ps.WaitForExit(1000);*/
            System.Diagnostics.Process p1 = new System.Diagnostics.Process("notepad.exe");
            p1.Start();
        }

        protected override void OnStop()
        {
        }
    }
}
