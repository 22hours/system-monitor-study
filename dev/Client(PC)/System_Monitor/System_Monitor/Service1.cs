using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;

namespace System_Monitor
{
    public partial class Service1 : ServiceBase
    {
        private const string folderPath = "C:\\Users\\damin\\Downloads";
        private const string filePath = folderPath + "\\service.txt";
        StreamWriter sw;
        public Service1()
        {
            InitializeComponent();
        }

        protected override void OnStart(string[] args)
        {
            string applicationName = "SystemMonitor.exe";
            //string applicationName = "C:\\Users\\damin\\source\\repos\\System_Monitor\\System_Monitor\\bin\\Release\\SystemMonitor.exe";
            ApplicationLoader.PROCESS_INFORMATION procInfo;
            ApplicationLoader.StartProcessAndBypassUAC(applicationName, out procInfo);
        }


        protected override void OnStop()
        {
        }
    }
}
