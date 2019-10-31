#ifndef CLIENTPC_H
#define CLIENTPC_H

#include <QMainWindow>

namespace Ui {
class ClientPC;
}

class ClientPC : public QMainWindow
{
    Q_OBJECT

public:
    explicit ClientPC(QWidget *parent = 0);
    ~ClientPC();

private:
    Ui::ClientPC *ui;
};

#endif // CLIENTPC_H
