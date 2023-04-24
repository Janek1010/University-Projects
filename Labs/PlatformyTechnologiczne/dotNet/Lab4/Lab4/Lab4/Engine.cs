using System.Xml.Serialization;

namespace Lab4;

[XmlRoot(ElementName = "engine")]
public class Engine : IComparable
{
    private double displacement;
    private double horsePower;

    [XmlAttribute]
    private string model;
    public Engine() { }
    public Engine(double displacement, double horsePower, string model)
    {
        this.displacement = displacement;
        this.horsePower = horsePower;
        this.model = model;
    }
    public double Displacement
    {
        get { return displacement; }
        set { displacement = value; }
    }

    public double HorsePower
    {
        get { return horsePower; }
        set { horsePower = value; }
    }

    public string Model
    {
        get { return model; }
        set { model = value; }
    }

    public int CompareTo(object obj)
    {
        var oth = (Engine)obj;
        if (model.CompareTo(oth.model) != 0)
        {
            return model.CompareTo(oth.model);
        }
        else if (displacement.CompareTo(oth.displacement) != 0)
        {
            return displacement.CompareTo(oth.displacement);
        }
        return horsePower.CompareTo(oth.horsePower);
    }
}