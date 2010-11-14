from django.utils import simplejson
from django.core.serializers.json import DateTimeAwareJSONEncoder
from piston.emitters import Emitter
import simplejson


class ExtJSONEmitter(Emitter):  
    def render(self, request):
        print request
        ext_dict = {"nothing":1}
        seria = simplejson.dumps(ext_dict, cls=DateTimeAwareJSONEncoder, ensure_ascii=False, indent=4)
        return seria             
        
        
Emitter.register('ext-json', ExtJSONEmitter, 'application/json; charset=utf-8')
           

