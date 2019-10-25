#include "clientpc.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    ClientPC w;
    w.show();

    return a.exec();
}
