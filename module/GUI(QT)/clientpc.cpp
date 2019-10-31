#include "clientpc.h"
#include "ui_clientpc.h"

ClientPC::ClientPC(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::ClientPC)
{
    ui->setupUi(this);
}

ClientPC::~ClientPC()
{
    delete ui;
}
