
public class Weapons
{
    
    enum WEAPONS
    {
        
        // NAME (Name, Damage dealt, Critical chance, Defense boost, Effect);
        FENCER ("LBlade", 2, 3, 0, 2),
        BROADS ("HSword", 4, 0, 1, 4),
        BATTLEAX ("FlAx", 5, 1, 1, 6),
        FLAIL ("FlAx",4, 2, 1, 5),
        QSTAFF ("Poles", 3, 3, 2, 4),
        BOXGLOV ("Gloves", 1, 3, 0, 1),
        WOODBOW ("Bows", 1, 0, 1, 2),
        ROD ("Magic", 2, 2, 0, 1),
        STAFF ("Magic", 2, 1, 1, 3),
        ORB ("Magic", 1, 3, 0, 2),
        ARROW ("Bows", 3, 2, 0, 1)
        ;
        
        private String type;
        public int atk;
        private int dmg;
        private int crit;
        private int weight;
        WEAPONS (String type, int atk, int dmg, int crit, int weight)
        {
            this.type = type;
            this.atk = atk;
            this.dmg = dmg;
            this.crit = crit;
            this.weight = weight;
        }
        
        public String getType()
        {
            return type;
        }
        
        public int getAtk()
        {
            return atk;
        }
        
        public int getDmg()
        {
            return dmg;
        }
        
        public int getCrit()
        {
            return crit;
        }
        
        public int getWei()
        {
            return weight;
        }
    }
    
}
