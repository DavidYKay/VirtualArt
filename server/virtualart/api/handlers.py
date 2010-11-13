from piston.handler import BaseHandler
from virtualart.models import *



class SimpleArtHandler(BaseHandler):
    #allowed_methods = ('GET', 'POST', 'DELETE',)
    model = SimpleArt    
    #def read(self, request, art_id=None):
    #    return SimpleArt.objects.all()                      
        
                
class ComplexArtHandler(BaseHandler):
    model = ComplexArt
