from django.conf.urls.defaults import *
from piston.resource import Resource
from server.virtualart.api.handlers import *

from server.virtualart.views import *

#simpleart_resource = Resource(SimpleArtHandler)
#complexart_resource = Resource(ComplexArtHandler)
#geo_resource = Resource(GeoHandler)

art_resource = Resource(ArtHandler)
geo_resource = Resource(GeoArtHandler)

urlpatterns = patterns('',
    #url(r'^art/(?P<lat>)/(?P<lon>)/(?P<range>)$', geo_resource),
    url(r'^art/(?P<lat>\d+\.\d+)/(?P<lon>\d+\.\d+)/(?P<range>\d+\.\d+)$', geo_resource),
    url(r'^art/(?P<lat>(-)*\d+\.\d+)/(?P<lon>(-)*\d+\.\d+)/(?P<range>\d+\.\d+)$', geo_resource),
    #url(r'^simpleart/(?P<id>\d+)$', simpleart_resource),  
    #url(r'^simpleart$', simpleart_resource),
    #url(r'^art/(?P<id>\d+)$', art_resource, { 'emitter_format': 'ext-json' }),  
    #url(r'^art$', art_resource, { 'emitter_format': 'ext-json' }),
    url(r'^art/(?P<id>\d+)$', art_resource),  
    url(r'^art$', art_resource),
    url(r'^test$', test_upload),
)
