import java.awt.*;
import javax.swing.*;
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            JOptionPane.showMessageDialog(null, "Deposited: \u20B9" + amount);
        } else {
            JOptionPane.showMessageDialog(null, "Deposit amount must be positive!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance-500) {
                balance -= amount;
                JOptionPane.showMessageDialog(null, "Withdrawn: \u20B9" + amount);
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient balance!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Withdrawal amount must be positive!");
        }
    }
}
public class ATMUI extends JFrame {
    private BankAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;

    public ATMUI(BankAccount account) {
        this.account = account;
        setTitle("ATM Machine");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        balanceLabel = new JLabel("Balance: \u20B9" + account.getBalance(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        amountField = new JTextField();
        amountField.setHorizontalAlignment(JTextField.CENTER);

        JButton checkBalanceBtn = new JButton("Check Balance");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton exitBtn = new JButton("Exit");

        checkBalanceBtn.addActionListener(e -> JOptionPane.showMessageDialog(null,"Balance: \u20B9" + account.getBalance()));

        depositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.deposit(amount);
                balanceLabel.setText("Balance: \u20B9" + account.getBalance());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter a valid number!");
            }
        });

        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.withdraw(amount);
                balanceLabel.setText("Balance: \u20B9" + account.getBalance());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter a valid number!");
            }
        });

        exitBtn.addActionListener(e -> System.exit(0));

        add(balanceLabel);
        add(amountField);
        add(checkBalanceBtn);
        add(depositBtn);
        add(withdrawBtn);
        add(exitBtn);

        setVisible(true);
    }

    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.0);
        new ATMUI(userAccount);
    }
}
