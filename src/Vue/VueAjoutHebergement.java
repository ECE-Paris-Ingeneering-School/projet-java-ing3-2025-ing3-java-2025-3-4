package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAjoutHebergement extends JFrame {

    private JTextField champNom;
    private JTextField champAdresse;
    private JTextField champVille;
    private JTextField champPays;
    private JComboBox<String> comboCategorie;
    private JTextField champPrix;
    private JTextArea champDescription;
    private JButton boutonAjouter;
    private JButton boutonRetour;

    public VueAjoutHebergement() {
        setTitle("Ajouter un Hébergement");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelFormulaire = new JPanel(new GridLayout(8, 2, 10, 10));
        panelFormulaire.setBorder(BorderFactory.createTitledBorder("Formulaire d'ajout"));

        champNom = new JTextField();
        champAdresse = new JTextField();
        champVille = new JTextField();
        champPays = new JTextField();

        String[] categories = {"Appartement", "Hôtel", "Maison", "Camping", "Auberge"};
        comboCategorie = new JComboBox<>(categories);

        champPrix = new JTextField();
        champDescription = new JTextArea(3, 20);
        champDescription.setLineWrap(true);
        champDescription.setWrapStyleWord(true);
        JScrollPane scrollDescription = new JScrollPane(champDescription);

        boutonAjouter = new JButton("Ajouter");
        boutonAjouter.setActionCommand("AJOUTER_HEBERGEMENT");

        boutonRetour = new JButton("Retour");
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        panelFormulaire.add(new JLabel("Nom :"));
        panelFormulaire.add(champNom);
        panelFormulaire.add(new JLabel("Adresse :"));
        panelFormulaire.add(champAdresse);
        panelFormulaire.add(new JLabel("Ville :"));
        panelFormulaire.add(champVille);
        panelFormulaire.add(new JLabel("Pays :"));
        panelFormulaire.add(champPays);
        panelFormulaire.add(new JLabel("Catégorie :"));
        panelFormulaire.add(comboCategorie);
        panelFormulaire.add(new JLabel("Prix par nuit (€) :"));
        panelFormulaire.add(champPrix);
        panelFormulaire.add(new JLabel("Description :"));
        panelFormulaire.add(scrollDescription);
        panelFormulaire.add(boutonRetour);
        panelFormulaire.add(boutonAjouter);

        setLayout(new BorderLayout());
        add(panelFormulaire, BorderLayout.CENTER);
    }

    public String getNom() { return champNom.getText(); }
    public String getAdresse() { return champAdresse.getText(); }
    public String getVille() { return champVille.getText(); }
    public String getPays() { return champPays.getText(); }
    public String getCategorie() { return (String) comboCategorie.getSelectedItem(); }
    public String getPrix() { return champPrix.getText(); }
    public String getDescription() { return champDescription.getText(); }

    public void ajouterEcouteur(ActionListener listener) {
        boutonAjouter.addActionListener(listener);
        boutonRetour.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void resetChamps() {
        champNom.setText("");
        champAdresse.setText("");
        champVille.setText("");
        champPays.setText("");
        comboCategorie.setSelectedIndex(0);
        champPrix.setText("");
        champDescription.setText("");
    }
}
