package org.opentripplanner.netex.mapping;

import java.util.ArrayList;
import java.util.List;
import net.opengis.gml._3.AbstractRingPropertyType;
import net.opengis.gml._3.LinearRingType;
import net.opengis.gml._3.PolygonType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.opentripplanner.api.resource.CoordinateArrayListSequence;
import org.opentripplanner.common.geometry.GeometryUtils;

/**
 * This maps from the OpenGIS PolygonType to LocationTech geometry.
 */
class OpenGisMapper {

  static Geometry mapGeometry(PolygonType polygonType) {
    return new Polygon(
      new LinearRing(
        mapCoordinateSequence(polygonType.getExterior()),
        GeometryUtils.getGeometryFactory()
      ),
      polygonType
        .getInterior()
        .stream()
        .map(c -> new LinearRing(mapCoordinateSequence(c), GeometryUtils.getGeometryFactory()))
        .toArray(LinearRing[]::new),
      GeometryUtils.getGeometryFactory()
    );
  }

  private static CoordinateSequence mapCoordinateSequence(
    AbstractRingPropertyType abstractRingPropertyType
  ) {
    List<Double> posList =
      ((LinearRingType) abstractRingPropertyType.getAbstractRing().getValue()).getPosList()
        .getValue();

    // Convert a single list of alternating lat/lon values into coordinates
    ArrayList<Coordinate> coordinates = new ArrayList<>();
    for (int i = 0; i < posList.size(); i += 2) {
      coordinates.add(new Coordinate(posList.get(i + 1), posList.get(i)));
    }

    return new CoordinateArrayListSequence(coordinates);
  }
}
