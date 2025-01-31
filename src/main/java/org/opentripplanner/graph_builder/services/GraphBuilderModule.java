package org.opentripplanner.graph_builder.services;

import java.util.HashMap;
import org.opentripplanner.graph_builder.DataImportIssueStore;
import org.opentripplanner.routing.graph.Graph;
import org.opentripplanner.transit.service.TransitModel;

/** Modules that add elements to a graph. These are plugins to the GraphBuilder. */
public interface GraphBuilderModule {
  /**
   * Process whatever inputs were supplied to this module and add the resulting elements to the
   * given graph.
   */
  void buildGraph(
    Graph graph,
    TransitModel transitModel,
    HashMap<Class<?>, Object> extra,
    DataImportIssueStore issueStore
  );

  default void buildGraph(Graph graph, TransitModel transitModel, HashMap<Class<?>, Object> extra) {
    buildGraph(graph, transitModel, extra, new DataImportIssueStore(false));
  }

  /** Check that all inputs to the graphbuilder are valid; throw an exception if not. */
  void checkInputs();
}
