<?xml version="1.0" encoding="UTF-8"?>
<tileset name="pixel-art" tilewidth="32" tileheight="32" tilecount="10" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/box-cover.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="1">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/horizontal-lower.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="2">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/horizontal-upper.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="3">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/left-upper-outer-corner.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="4">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/right-lower-outer-corner.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="5">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/right-upper-outer-corner.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="6">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/vertical-left.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="7">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/vertical-right.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="8">
  <properties>
   <property name="collision" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="sprites/left-lower-outer-corner.png"/>
  <objectgroup draworder="index">
   <object id="1" name="collision" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="10">
  <properties>
   <property name="collision" type="bool" value="false"/>
  </properties>
  <image width="32" height="32" source="sprites/ground.png"/>
 </tile>
</tileset>
