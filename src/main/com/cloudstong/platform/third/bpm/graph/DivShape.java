package com.cloudstong.platform.third.bpm.graph;

public class DivShape extends Shape
{
  private int zIndex = 0;
  private String id = "";
  private String type = "";

  public String getType() {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public DivShape(String name, float x, float y, float w, float h)
  {
    super(name, x, y, w, h);
  }

  public DivShape(String name, float x, float y, float w, float h, int zIndex, String id, String type)
  {
    super(name, x, y, w, h);
    this.zIndex = zIndex;
    this.id = id;
    this.type = type;
  }

  public int getzIndex()
  {
    return zIndex;
  }

  public void setzIndex(int zIndex)
  {
    this.zIndex = zIndex;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String toString()
  {
    return "<div class='flowNode' id='" + id + "' " + 
      "style='position:absolute;z-index:" + zIndex + "; " + 
      "left:" + getX() + "px;" + 
      "top:" + getY() + "px;" + 
      "width:" + getW() + "px;" + 
      "height:" + getH() + "px;' " + 
      "title='" + getName() + "' " + 
      "type='" + getType() + "'></div>\r\n".replaceAll("\\.0px", "px");
  }
}