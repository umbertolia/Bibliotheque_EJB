import metier.PropertytEnumAvecFils;

public class EnumTreeNested {
    static void visit(Class<?> clazz) {
        Object[] enumConstants = clazz.getEnumConstants();
        if (enumConstants[0] instanceof PropertytEnumAvecFils) for (Object o : enumConstants)
            visit((Citrus) o, clazz.getName());
    }
    static void visit(PropertytEnumAvecFils hasChildren, String prefix) {
        if (hasChildren instanceof Enum) {
            System.out.println(prefix + ' ' + hasChildren);
            if (hasChildren.getEnfants() != null) for (Object o : hasChildren.getEnfants())
                visit((PropertytEnumAvecFils) o, prefix + ' ' + hasChildren);
        } else
            System.out.println("other " + hasChildren.getClass());
    }
   
 
    public static void main(String[] args) {
       /* System.out.println(Citrus.Orange.Navel.washinton);
        visit(Citrus.lemon, "");
        System.out.println("----------------------");
        visit(Citrus.orange, "");
        System.out.println("----------------------");*/
        visit(Citrus.class);
        System.out.println("----------------------");
    }
}