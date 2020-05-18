using System;
using System.Diagnostics;
using System.IO;
using System.Management;
using System.ServiceProcess;
using System.Threading;
using System.Timers;

namespace System_Monitor
{
    public partial class Service1 : ServiceBase
    {
        private System.Timers.Timer timer;
        private const string folderPath = "C:\\Users\\damin\\Downloads";
        private const string filePath = folderPath + "\\service.txt";
        StreamWriter sw;
        public Service1()
        {
            InitializeComponent();
        }

        protected override void OnStart(string[] args)
        {
            startSystem();
            timer = new System.Timers.Timer();
            timer.Interval = 3000; // 3초마다 실행
            timer.Elapsed += Timer_Elapsed;
            timer.Start();
            
        }

        private bool isitRunning()
        {
            Process[] allProc = Process.GetProcessesByName("javaw");
            foreach (Process p in allProc)
            {
                int pid = p.Id;
                if (GetProcessOwner(pid).Equals("SYSTEM")) return true;
                
            }
            return false;
        }

        private void Timer_Elapsed(object sender, ElapsedEventArgs e)
        {
            if (!isitRunning())
            {
                timer.Stop();
                startSystem();
                timer.Start();
            }
        }

        public void startSystem()
        {
            string applicationName = "SystemMonitor.exe";
            //string applicationName = "C:\\Users\\damin\\source\\repos\\System_Monitor\\System_Monitor\\bin\\Release\\SystemMonitor.exe";
            ApplicationLoader.PROCESS_INFORMATION procInfo;
            ApplicationLoader.StartProcessAndBypassUAC(applicationName, out procInfo);
            Thread.Sleep(10000); // 10초
        }

        private void writeLog(String msg)
        {
            sw = new StreamWriter(filePath, true);
            sw.WriteLine(msg);
            sw.Close();
        }

        protected override void OnStop()
        {
            if (isitRunning())
            {
                Process[] allProc = Process.GetProcessesByName("javaw");
                foreach (Process p in allProc)
                {
                    int pid = p.Id;
                    if (GetProcessOwner(pid).Equals("SYSTEM"))
                            p.Kill();
                }
            }
        }
        public string GetProcessOwner(int pid)
        {
            try
            {
                string query = "SELECT * FROM WIN32_PROCESS WHERE ProcessID = " + pid;
                ManagementObjectSearcher searcher = new ManagementObjectSearcher(query);
                ManagementObjectCollection processList = searcher.Get();

                foreach (ManagementObject obj in processList)
                {
                    string[] argList = new string[] { string.Empty, string.Empty };
                    int retVal = Convert.ToInt32(obj.InvokeMethod("GetOwner", argList));
                    return argList[0];
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            return null;
        }
    }
}
