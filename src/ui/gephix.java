package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;
import javax.swing.JFrame;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.graph.DegreeRangeBuilder.DegreeRangeFilter;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractColorTransformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;

import processing.core.PApplet;

public class gephix extends JFrame {

	public static JFrame gephiframe;
	
	public gephix() {
		initialize();
	}
		
	public void initialize(){
		// TODO 自动生成的方法存根
		//Init a project - and therefore a workspace
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();
		
		//Get models and controllers for this new workspace - will be useful later
		AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		PreviewModel model = Lookup.getDefault().lookup(PreviewController.class).getModel();
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
		RankingController rankingController = Lookup.getDefault().lookup(RankingController.class);
		
		//Import file     
		Container container;
		try {
		    File file = new File(getClass().getResource("/resources/graph.gexf").toURI());//polblogs.gml    
		    //File file = new File(getClass().getResource("/resources/timeframe1.gexf").toURI());
		    container = importController.importFile(file);
		    container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);  //Force DIRECTED
		} catch (Exception ex) {
		    ex.printStackTrace();
		    return;
		}
		//Append imported data to GraphAPI
		importController.process(container, new DefaultProcessor(), workspace);
		
		//See if graph is well imported
		DirectedGraph graph = graphModel.getDirectedGraph();
		System.out.println("Nodes: " + graph.getNodeCount());
		System.out.println("Edges: " + graph.getEdgeCount());
		
		//Filter     
		DegreeRangeFilter degreeFilter = new DegreeRangeFilter();
		degreeFilter.init(graph);
		degreeFilter.setRange(new Range(0, Integer.MAX_VALUE));    //Remove nodes with degree < 30
		Query query = filterController.createQuery(degreeFilter);
		GraphView view = filterController.filter(query);
		graphModel.setVisibleView(view);    //Set the filter result as the visible view
		
		//See visible graph stats
		UndirectedGraph graphVisible = graphModel.getUndirectedGraphVisible();
		System.out.println("Visible Nodes: " + graphVisible.getNodeCount());
		System.out.println("Visible Edges: " + graphVisible.getEdgeCount());
		
		//Run YifanHuLayout for 100 passes - The layout always takes the current visible view
		YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
		layout.setGraphModel(graphModel);
		layout.resetPropertiesValues();
		layout.setOptimalDistance(200f);
		layout.initAlgo();
		  
		for (int i = 0; i < 100 && layout.canAlgo(); i++) {
		    layout.goAlgo();
		}
		layout.endAlgo();
		
		//Rank color by Degree
		Ranking<?> degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
		AbstractColorTransformer<?> colorTransformer = (AbstractColorTransformer<?>) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);
		colorTransformer.setColors(new Color[]{new Color(0xFEF0D9), new Color(0xB30000)});
		rankingController.transform(degreeRanking,colorTransformer);
		
		//Get Centrality
		GraphDistance distance = new GraphDistance();
        distance.setDirected(true);
        distance.execute(graphModel, attributeModel);
        
        //Rank size by centrality
		AttributeColumn centralityColumn = attributeModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS); // GraphDistance.BETWEENNESS = betweenesscentrality
		Ranking<?> centralityRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, centralityColumn.getId());
		AbstractSizeTransformer<?> sizeTransformer = (AbstractSizeTransformer<?>) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
		sizeTransformer.setMinSize(3);
		sizeTransformer.setMaxSize(10);
		rankingController.transform(centralityRanking,sizeTransformer);
		
		//Preview
		PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
		        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.GRAY));
		model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.1f));
		model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(8));
		previewController.refreshPreview();
		          
		//New Processing target, get the PApplet
		ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
		PApplet applet = target.getApplet();
		applet.init();
		 
		//Refresh the preview and reset the zoom
		previewController.render(target);
		target.refresh();
		target.resetZoom();
		
		gephiframe = new JFrame();
		gephiframe.setTitle("Graph Preview");
		gephiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gephiframe.setBounds(100, 100, 450, 300);
		gephiframe.getContentPane().add(applet, BorderLayout.CENTER);
		gephiframe.pack();
		gephiframe.setVisible(true);
		
		
	}
	
}
