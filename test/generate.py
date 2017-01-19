import gmplot

mymap = gmplot.GoogleMapPlotter(37.428, -122.145, 16)
# mymap = GoogleMapPlotter.from_geocode("Stanford University")

# mymap.grid(37.42, 37.43, 0.001, -122.15, -122.14, 0.001)
#mymap.marker(37.427, -122.145, "yellow")
#mymap.marker(37.428, -122.146, "cornflowerblue")
#mymap.marker(37.429, -122.144, "k")
# lat, lng = mymap.geocode("Stanford University")
# mymap.marker(lat, lng, "red")
# mymap.circle(37.429, -122.145, 100, "#FF0000", ew=2)
# path = [(37.429, 37.428, 37.427, 37.427, 37.427),
#          (-122.145, -122.145, -122.145, -122.146, -122.146)]
# path2 = [[i+.01 for i in path[0]], [i+.02 for i in path[1]]]
# path3 = [(37.433302 , 37.431257 , 37.427644 , 37.430303), (-122.14488, -122.133121, -122.137799, -122.148743)]
# path4 = [(37.423074, 37.422700, 37.422410, 37.422188, 37.422274, 37.422495, 37.422962, 37.423552, 37.424387, 37.425920, 37.425937),
#      (-122.150288, -122.149794, -122.148936, -122.148142, -122.146747, -122.14561, -122.144773, -122.143936, -122.142992, -122.147863, -122.145953)]
# mymap.plot(path[0], path[1], "plum", edge_width=10)
# mymap.plot(path2[0], path2[1], "red")
# mymap.polygon(path3[0], path3[1], edge_color="cyan", edge_width=5, face_color="blue", face_alpha=0.1)
# mymap.heatmap(path4[0], path4[1], threshold=10, radius=40)
# mymap.heatmap(path3[0], path3[1], threshold=10, radius=40, dissipating=False, gradient=[(30,30,30,0), (30,30,30,1), (50, 50, 50, 1)])
# mymap.scatter(path4[0], path4[1], c='r', marker=True)
# mymap.scatter(path4[0], path4[1], s=90, marker=False, alpha=0.1)
#     # Get more points with:
# # http://www.findlatitudeandlongitude.com/click-lat-lng-list/
scatter_path = ([37.424435, 37.424417, 37.424417, 37.424554, 37.424775, 37.425099, 37.425235, 37.425082, 37.424656, 37.423957, 37.422952, 37.421759, 37.420447, 37.419135, 37.417822, 37.417209],[-122.142048, -122.141275, -122.140503, -122.139688, -122.138872, -122.138078, -122.137241, -122.136405, -122.135568, -122.134731, -122.133894, -122.133057, -122.13222, -122.131383, -122.130557, -122.129999])
mymap.scatter(scatter_path[0], scatter_path[1], c='r', marker=True)
mymap.draw('./mymap.html')